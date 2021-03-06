package com.arjjs.ccm.modules.ccm.rest.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.arjjs.ccm.modules.ccm.message.entity.CcmMessage;
import com.arjjs.ccm.modules.ccm.message.service.CcmMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmRestResult;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmRestType;
import com.arjjs.ccm.modules.oa.service.OaMessageService;
import com.arjjs.ccm.modules.sys.entity.User;

/**
 * 工作职责
 * 我的消息查询接口
 * @author arj
 * @version 2018-03-12
 */
@Controller
@RequestMapping(value = "${appPath}/rest/oa/oaMessage")
public class CcmRestOaMessage extends BaseController {

	@Autowired
	private OaMessageService oaMessageService;

	@Autowired
	private CcmMessageService ccmMessageService;

	/**
	 * @see 获取单个我的消息
	 * @param id 工作职责ID
	 * @return
	 * @author arj
	 * @version 2018-03-12
	 */

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public CcmRestResult get(String userId, HttpServletRequest req, HttpServletResponse resp, String id)
			throws IOException {
		CcmRestResult result = new CcmRestResult();
		
		if (id == null || "".equals(id)) {//参数id不对
			result.setCode(CcmRestType.ERROR_PARAM);
			return result;
		}
		if (userId == null || "".equals(userId)) {//参数id不对
			result.setCode(CcmRestType.ERROR_PARAM);
			return result;
		}

		User user = new User();
		user.setId(userId);
		
//		OaMessage oaMessage = oaMessageService.get(id);
		CcmMessage ccmMessage = ccmMessageService.get(id);

		if(ccmMessage==null){
			return result;
		}
		//oaMessage.setId(id);
//		oaMessageService.updateReadFlag(oaMessage,user);//app跟新我的通告状态
//		oaMessage = oaMessageService.getRecordList(oaMessage);//app查询我的通告
		//查询详情是将阅读状态改为已阅
		if(ccmMessage.getReadFlag().equals("0")){
			ccmMessage.setReadFlag("1");
			ccmMessageService.updateRead(ccmMessage);
		}
		result.setCode(CcmRestType.OK);
		result.setResult(ccmMessage);

		return result;
	}

	/**
	 * @see 查询我的消息
	 * @param pageNo  页码
	 * @param pageSiz 分页大小
	 * @return
	 * @author arj
	 * @version 2018-03-12
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public CcmRestResult query(String userId, HttpServletRequest req, HttpServletResponse resp, CcmMessage ccmMessage) throws IOException {
		CcmRestResult result = new CcmRestResult();

		if (userId == null || "".equals(userId)) {//参数id不对
			result.setCode(CcmRestType.ERROR_PARAM);
			return result;
		}

//		oaMessage.setSelf(true);
//		oaMessage.setId(userId);//userId借用id
//		Page<OaMessage> page = oaMessageService.findApp(new Page<OaMessage>(req, resp), oaMessage);

		List<CcmMessage> listTodayAndUnread = ccmMessageService.getListTodayAndUnreadBymessage(ccmMessage);
		result.setCode(CcmRestType.OK);
		result.setResult(listTodayAndUnread);
		
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
}