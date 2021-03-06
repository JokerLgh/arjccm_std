/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.flat.appupload.service;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.service.CrudService;
import com.arjjs.ccm.modules.flat.appupload.entity.SysAppUpload;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import com.arjjs.ccm.tool.ApkInfo;
import com.arjjs.ccm.tool.AppReadUtil;
import com.arjjs.ccm.tool.exception.ServerErrorException;


import com.arjjs.ccm.modules.flat.appupload.dao.SysAppUploadDao;


/**
 * App 上传记录表Service
 * @author maoxb
 * @version 2019-05-16
 */
@Service
@Transactional(readOnly = true)
public class SysAppUploadService extends CrudService<SysAppUploadDao, SysAppUpload> {

	@Autowired
	Environment env;
	
	@Autowired
	public SysAppUploadDao sysAppUploadDao;
	
	public static final String PREFIX_APP  = "${adminPath}/appupload/d/";
	
	private String fileServerUrl = Global.getConfig("FILE_UPLOAD_URL");// 文件服务器路径



	public SysAppUpload get(String id) {
		return super.get(id);
	}
	
	public List<SysAppUpload> findList(SysAppUpload sysAppUpload) {
		return super.findList(sysAppUpload);
	}



	public Page<SysAppUpload> findPage(Page<SysAppUpload> page, SysAppUpload sysAppUpload) {
		/*
		 * List<SysAppUpload> findList = this.findList(sysAppUpload); List<SysAppUpload>
		 * findListResult = new ArrayList<SysAppUpload>();//this.findList(sysAppUpload);
		 * for (SysAppUpload appUploadInfo : findList) { String files =
		 * appUploadInfo.getFiles(); if (StringUtils.isNotBlank(files) ) { String
		 * substring = files.substring(0, 1); if ("|".equals(substring)) { String
		 * substring2 = files.substring(1, files.length());
		 * appUploadInfo.setFiles(fileServerUrl +substring2 );
		 * findListResult.add(appUploadInfo); } } } page.setList(findListResult);
		 */
		return super.findPage(page, sysAppUpload);
	}
	
	@Transactional(readOnly = false)
	public void save(SysAppUpload sysAppUpload) {
		String path = Global.getConfig("FILE_UPLOAD_PATH");
		String files = sysAppUpload.getFiles();
		String encode = null;
		if (StringUtils.isNotBlank(files) ) {
			String substring = files.substring(0, 1);
			if ("|".equals(substring)) {
				String substring2 = files.substring(1, files.length());
				sysAppUpload.setUrl(fileServerUrl +substring2 );
				try {
					encode = URLDecoder.decode(path + substring2, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				File getfiles = new File( encode);
				sysAppUpload.setSize(String.valueOf(getfiles.length()));
			}else {
				sysAppUpload.setUrl(substring );
			}
		}
		//设置最新版App状态(取出旧的，然后改为0，然后设置新的)
		SysAppUpload appInfo = dao.getAppInfo();
		if (appInfo != null) {
			appInfo.setStatus("0");
			appInfo.setUpdateBy(UserUtils.getUser());
			appInfo.setUpdateDate(new Date());
			dao.update(appInfo);
		}
		sysAppUpload.setStatus("1");
		super.save(sysAppUpload);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysAppUpload sysAppUpload) {
		super.delete(sysAppUpload);
	}
	//获取最新版本App
	public SysAppUpload getAppInfo() {
		 SysAppUpload appInfo = dao.getAppInfo();
		 if (appInfo == null) {
			 return new SysAppUpload();
		}
		return appInfo;
	}
	public void downloadAppFile(String id, HttpServletRequest request, HttpServletResponse response)  throws IOException {
		
		String localPath = env.getProperty("file.path")  + "/app/";
		
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String basePath = request.getSession().getServletContext().getRealPath("/");
		// 获取appUpload实体
		SysAppUpload appUpload = get(id);
		// 文件没找到
		if (appUpload == null) {
			response.getWriter().append("APK NOT FOUND!");
		} else {
			// 获取文件名
			String fileName = appUpload.getName();
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");// 火狐兼容
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			/*String contentType = attachment.getContentType();
			// 设置文件输出类型
			if (StringUtils.isBlank(contentType)) {
				contentType = "application/octet-stream";
			}*/
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "inline; filename=" + fileName);
			// 获取输入流
			bis = new BufferedInputStream(new FileInputStream(basePath + localPath + appUpload.getId()));
			// 设置输出长度
			response.setHeader("Content-Length", String.valueOf(appUpload.getSize()));
			// 输出流
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			// 关闭流
			bis.close();
			bos.close();
		}
	}

	public SysAppUpload uploadFile(MultipartFile file, HttpServletRequest request)  throws IOException {
		SysAppUpload appUpload = null;
		// 获取文件的名字
		String appName = file.getOriginalFilename();
		// 获取文件扩展名
		String suffix = appName.substring(appName.lastIndexOf(".") + 1);

		if (!suffix.equals("apk")) {
			throw new ServerErrorException("上传的文件不是apk文件，请重新选择文件");
		}
		ApkInfo readAPK = AppReadUtil.readAPK(env, file);
		if (readAPK == null) {
			throw new ServerErrorException("读取APK包异常，请检查文件");
		}
		if (readAPK.getVersionCode() == null) {
			throw new ServerErrorException("当前apk版本 versionCode 为空，请联系管理员！");
		}
		//获取配置文件中的服务器域名
		//http://34.121.33.250:8080/api  +  ${adminPath}/appupload/d/
		String filesp = env.getProperty("file.server.prefix") + PREFIX_APP;
		String filePath = env.getProperty("file.path");
		
		// 获取传过来的MD5值
		String md5 = request.getHeader("MD5-T");
		if (!StringUtils.isBlank(md5)) {
			// 通过MD5值查询数据库中已经存在的附件信息
			appUpload = sysAppUploadDao.getAppByMd5(md5);
		}
		// 如果数据库中没有附件
		if (appUpload == null) {
			// 获取文件的上传地址
			String basePath = request.getSession().getServletContext().getRealPath("/");
			String localPath = filePath + "/app/";
			// 获取文件新名字
			String appUploadId = UUID.randomUUID().toString();
			localPath += appUploadId;
			
			// 文件的大小
			int fileSize = (int) file.getSize();
			// 生成MD5
			String md5str = DigestUtils.md5Hex(file.getInputStream());
			// 创建附件实体
			appUpload = new SysAppUpload();
			appUpload.setId(appUploadId);
			appUpload.setName(appName);
			appUpload.setSize(String.valueOf(fileSize));
			appUpload.setMd5Value(md5str);
			appUpload.setUrl(filesp + appUploadId + "." + suffix);
			// 保存文件
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(basePath + localPath));
			
			//如果版本号不为空，是apk文件，更新app文件上传表
			if (StringUtils.isBlank(readAPK.getVersionCode().toString())) {
				throw new ServerErrorException("apk 版本信息异常，请检查apk版本信息!");
			}
			appUpload.setVersion(readAPK.getVersionCode().toString());
			this.save(appUpload);
		}
		return appUpload;
	}
	
}