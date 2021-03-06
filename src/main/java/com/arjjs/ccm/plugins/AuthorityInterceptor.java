package com.arjjs.ccm.plugins;

import com.arjjs.ccm.common.persistence.interceptor.BaseInterceptor;
import com.arjjs.ccm.common.utils.Reflections;
import com.arjjs.ccm.common.utils.SpringContextHolder;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.modules.ccm.org.service.SysAreaService;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.modules.sys.entity.Office;
import com.arjjs.ccm.modules.sys.entity.User;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * 拦截查询SQL，根据分页对象自动设置分页参数
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })

public class AuthorityInterceptor extends BaseInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static SysAreaService sysAreaService = SpringContextHolder.getBean(SysAreaService.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		Object proceed = invocation.proceed();

		final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		// 拦截需要分页的SQL
		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Object parameterObject = boundSql.getParameterObject();
		
		// 如果是对区域、部门的操作，则直接跳过
		// TODO  此处需要修改 跳过会造成部分数据权限异常
		if ((parameter instanceof Area) || (parameter instanceof Office) || (parameter instanceof User) || (parameter instanceof InterceptorEntity)) {
			return proceed;
		}
		User loginUser = null;//声明当前用户
		User check_user = null;//查询条件中的用户

		try {
			check_user = (User)Reflections.getFieldValue(parameterObject, "checkUser");;
		} catch (Exception e) {
		}
		if (check_user != null) {
			loginUser = check_user;
			if (loginUser.isAdmin()) {//app登录，用户是admin时，直接返回运行结果，不进行拦截
				return proceed;
			}
		}
		if (loginUser == null) {//非app调用
			if (UserUtils.getPrincipal() == null || UserUtils.getUser().isAdmin()) {//单点登录，用户是admin时，直接返回运行结果，不进行拦截
				return proceed;
			}
			loginUser = UserUtils.getUser();
		}

		if (StringUtils.isBlank(boundSql.getSql())) {
			return null;
		}
//		String sql = buildSql(UserUtils.getUser(), boundSql.getSql().trim());
		String sql = buildSql(loginUser, boundSql.getSql().trim());

		if (sql == null) {
			return proceed;
		}

		BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings(),
				boundSql.getParameterObject());
		// 解决MyBatis 分页foreach 参数失效 start
		if (Reflections.getFieldValue(boundSql, "metaParameters") != null) {
			MetaObject mo = (MetaObject) Reflections.getFieldValue(boundSql, "metaParameters");
			Reflections.setFieldValue(newBoundSql, "metaParameters", mo);
		}
		MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
		invocation.getArgs()[0] = newMs;
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 生成SQL
	 * 
	 * @param sql
	 * @return
	 */
	private String buildSql(User user, String sql) {
		StringBuffer sqlRet = new StringBuffer();
		StringBuffer sqlSB = new StringBuffer(sql);
		if (user.getOffice() != null) {
			// TODO 当前为用户所属区域及以下区域数据权限，若需要系统设置中的权限控制，则需添加代码
			sql = sql.toLowerCase();
			String areaAlias = getAlias("sys_area", sql);
			if (areaAlias != null) {
				StringBuilder idString = new StringBuilder();
				//查詢子 id 及以下sys_log
				String parentIds = user.getOffice().getArea().getParentIds();
				String id = user.getOffice().getArea().getId();
				InterceptorEntity  interceptorEntity = new InterceptorEntity();
				interceptorEntity.setId(id);
				interceptorEntity.setParentIds( parentIds + id );

				List<String> areaIds = sysAreaService.selectAreaIdByParentIdAndId(interceptorEntity);
				areaIds.forEach(areaId->{
					idString.append("'" + areaId + "',");
				});
				String substring = idString.substring(0, idString.length() - 1);
				//sql in
				if (user.getOffice() != null && user.getOffice().getArea() != null) {
					sqlRet.append(" "+  areaAlias + ".id in (" );
					sqlRet.append(substring );
					sqlRet.append(") and ");
				} else {//用户无归属部门，或者部门无归属区域，则查询失效
					sqlRet.append(" 1 = 2 and ");
				}
			}
		}

		
		if (sqlSB.toString().toLowerCase().lastIndexOf("where") <= 0) {
			return null;
		}else{
			sqlSB.insert(sqlSB.toString().toLowerCase().lastIndexOf("where") + "where".length(), sqlRet);
		}
		return sqlSB.toString();

	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
				ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null) {
			for (String keyProperty : ms.getKeyProperties()) {
				builder.keyProperty(keyProperty);
			}
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		builder.useCache(ms.isUseCache());
		return builder.build();
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	/**
	 * 用户区域权限
	 *  String buildSql(User user, String sql) {
	 * @param user
	 * @param userAlias
	 * @return SQL语句
	 */
	public static String dataAreaFilter(User user, String sql) {
		StringBuilder result = new StringBuilder();

		return result.toString();
	}

	/**
	 * 在sql中获取指定表的别名
	 * 
	 * @param table
	 * @param sql
	 * @return
	 */
	public static String getAlias(String table, String sql) {
		String result = null;
		int tabBegin = sql.lastIndexOf(table);
		if (tabBegin <= 0) {
			return result;
		}
		String t = sql.substring(tabBegin + table.length()).trim();
		//SQL中的特殊字符转换为空格
		String[] regex=new String[]{",","=","\t","\n","\r"};
		for (int i=0;i<regex.length;i++){
			t=t.replace(regex[i], " ");
		}
		
		int tabEnd = t.indexOf(" ");
		
		String key = t.substring(0, tabEnd);
		if (!key.toLowerCase().trim().equals("as")) {
			result = key;
		} else {
			t = t.substring("as".length()).trim();
			tabEnd = t.indexOf(" ");
			result = t.substring(0, tabEnd);
		}
		return result;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String mSql = "		SELECT" 
				+ "			a.id AS ‘id‘," 
				+ "			a.parent_id AS ‘parent.id‘,"
				+ "			a.parent_ids AS ‘parentIds‘," 
				+ "			a. NAME AS ‘name‘,"
				+ "			'area' AS ‘typeId‘" 
				+ "		FROM" 
				+ "		sys_area a" 
				+ "	UNION ALL"
				+ "			SELECT" 
				+ "				d.id AS ‘id‘," 
				+ "				d.area_id AS ‘parent.id‘,"
				+ "				d.area_id AS ‘parentIds‘," 
				+ "				d. NAME AS ‘name‘,"
				+ "				'camera' AS ‘typeId‘" 
				+ "			FROM" 
				+ "				ccm_device d"
				+ "			WHERE" 
				+ "				d.type_id = '003'";
		StringBuffer b = new StringBuffer();
		String s = mSql.toLowerCase();
		String areaAlias = AuthorityInterceptor.getAlias("sys_area", s);
		System.out.println("areaAlias=" + areaAlias);

		StringBuilder sqlRet = new StringBuilder();
		if (areaAlias != null) {
			sqlRet.append(" " + areaAlias + ".parent_ids like ('");
			sqlRet.append("1,2,3,%");
			sqlRet.append("12%') and ");
		}
		StringBuilder sqlSB = new StringBuilder(mSql);
		if (mSql.toLowerCase().lastIndexOf("where") <= 0) {
			return;
		}
		sqlSB.insert(mSql.toLowerCase().lastIndexOf("where") + "where".length(), sqlRet);
		System.out.println("sql=" + sqlSB.toString());

	}

}
