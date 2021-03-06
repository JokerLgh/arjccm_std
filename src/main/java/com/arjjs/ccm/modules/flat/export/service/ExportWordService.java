package com.arjjs.ccm.modules.flat.export.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.modules.flat.alarm.service.BphAlarmInfoService;
import com.arjjs.ccm.modules.flat.alarmhandlelog.dao.BphAlarmHandleLogDao;
import com.arjjs.ccm.modules.flat.export.entity.WordDataList;
import com.arjjs.ccm.modules.flat.handle.dao.BphAlarmHandleDao;
import com.arjjs.ccm.modules.sys.utils.DictUtils;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import com.arjjs.ccm.tool.WordUtils;
import com.arjjs.ccm.tool.ZipUtils;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;

@Service
@Transactional(readOnly = true)
public class ExportWordService {

	private static final String LOST_DOCUMENT = "（该文件已丢失）";
	private String WORD_PATH = this.getClass().getResource("/").getPath() + "\\alarmDocumentWord";
	@Autowired
	private BphAlarmInfoService bphAlarmInfoService;
	@Autowired
	private BphAlarmHandleDao bphAlarmHandleDao;
	@Autowired
	private BphAlarmHandleLogDao bphAlarmHandleLogDao;

	public byte[] queryAlarmAllData(String id) throws Exception {
		WordDataList wordDataList = new WordDataList();
		wordDataList.setAlarmInfo(bphAlarmInfoService.get(id));
		wordDataList.setAlarmHandleLogList(bphAlarmHandleLogDao.findByAlarmId(id));
		wordDataList.setWordAlarmHandleFilePlanStepActionDataList(bphAlarmHandleDao.findAlarmHandleFilePlanStepAction(id));
		File fileDir = createWord(wordDataList);
		return ZipUtils.toZip(fileDir.getAbsolutePath(), false);
	}

	/**
	 * 以图搜图 创建word文档 n:n
	 *
	 * @param exportWord
	 * @throws Exception
	 */
	public File createWord(WordDataList wordDataList) throws Exception {
		File fileDir = new File(WORD_PATH);
		// 删除文件
		if (fileDir.exists()) {
			File[] files = fileDir.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f.isFile()) {
						f.delete();
					}
				}
			}
			fileDir.delete();
		}
		// 创建目录
		fileDir.mkdirs();
		// word名称
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String wordName = wordDataList.getAlarmInfo().getPlace() + dateFormat.format(new Date()) + ".doc";
		// 生成的word文档的保存路径
		String savePath = fileDir + File.separator + wordName;
		Document document = new Document(PageSize.A4, 90, 90, 70, 70);
		// 建立一个书写器，与document对象关联
		RtfWriter2.getInstance(document, new FileOutputStream(savePath));
		document.open();
		// 设置中文字体
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		// //人脸对比报告名称字体风格
		// Font titleNameFont = new Font(bfChinese,28,Font.BOLD);
		// //封面文字
		// Font coverWritingFont = new Font(bfChinese,20,Font.BOLD);
		// //大标题字体风格
		// Font title1Font = new Font(bfChinese,20,Font.BOLD);
		// //正文标题字体风格
		// Font title2Font = new Font(bfChinese,14,Font.BOLD);
		// //正文字体风格
		// Font contextFont = new Font(bfChinese,12,Font.NORMAL);
		// 创建封面
		creataFirstPage(document, wordDataList, bfChinese);
		// 创建正文
		creataMainBody(document, wordDataList, bfChinese);
		// 关闭
		document.close();
		return fileDir;
	}

	/**
	 * 创建封面
	 *
	 * @param document
	 * @throws DocumentException
	 */
	private void creataFirstPage(Document document, WordDataList wordDataList, BaseFont bfChinese)
			throws DocumentException {
		for (int i = 0; i < 10; i++) {
			WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 12, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		}
		WordUtils.addLine(document, "警情事后归档", bfChinese, new Font(bfChinese, 28, Font.BOLD), Element.ALIGN_CENTER);
		for (int i = 0; i < 17; i++) {
			WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 12, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		WordUtils.addLine(document, "创建人:   " + UserUtils.getUser().getName(), bfChinese,
				new Font(bfChinese, 20, Font.BOLD), Element.ALIGN_CENTER);
		WordUtils.addLine(document, "创建时间:	" + date, bfChinese, new Font(bfChinese, 20, Font.BOLD),
				Element.ALIGN_CENTER);
		WordUtils.addLine(document, "创建人单位:	" + UserUtils.getUser().getOffice().getName(), bfChinese,
				new Font(bfChinese, 20, Font.BOLD), Element.ALIGN_CENTER);
		for (int i = 0; i < 14; i++) {
			WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 12, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		}
	}

	/**
	 * 创建正文
	 *
	 * @param document
	 * @throws Exception
	 */
	private void creataMainBody(Document document, WordDataList wordDataList, BaseFont bfChinese) throws Exception {
		String targetPath = WORD_PATH;
		WordUtils.addLine(document, "一、警情信息", bfChinese, new Font(bfChinese, 18, Font.BOLD), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "接警单编号: " + wordDataList.getAlarmInfo().getOrderNum(), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "接警员工号: " + wordDataList.getAlarmInfo().getPoliceNum(), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "接警员姓名: " + wordDataList.getAlarmInfo().getPoliceName(), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "报警人姓名: " + wordDataList.getAlarmInfo().getManName(), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document,
				"报警 人性别: " + DictUtils.getDictLabel(wordDataList.getAlarmInfo().getManSex(), "sex", ""), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "报警人联系电话: " + wordDataList.getAlarmInfo().getManTel(), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document,
				"报警方式: " + DictUtils.getDictLabel(wordDataList.getAlarmInfo().getAlarmFrom(), "bph_alarm_from", ""),
				bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "报警时间: " + WordUtils.DateUtils(wordDataList.getAlarmInfo().getAlarmTime()),
				bfChinese, new Font(bfChinese, 12, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document,
				"是否是重大警情: " + DictUtils.getDictLabel(wordDataList.getAlarmInfo().getIsImportant(),
						"bph_plan_alarm_level", ""),
				bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "案发地点: " + wordDataList.getAlarmInfo().getPlace(), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "案发地点经度: " + wordDataList.getAlarmInfo().getX(), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "案发地点纬度: " + wordDataList.getAlarmInfo().getY(), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "警情类型: " + wordDataList.getAlarmInfo().getTypeName(), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		if(wordDataList.getAlarmInfo().getArea() != null) {
			WordUtils.addLine(document, "区  域: " + wordDataList.getAlarmInfo().getArea().getName(), bfChinese,
					new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		}else {
			WordUtils.addLine(document, "区  域: " , bfChinese,new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		}
		if(wordDataList.getAlarmInfo().getOffice() != null) {
			WordUtils.addLine(document, "受理单位: " + wordDataList.getAlarmInfo().getOffice().getName(), bfChinese,
					new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		}
		WordUtils.addLine(document, "警情内容: " + wordDataList.getAlarmInfo().getContent(), bfChinese,
				new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		String path = "";
		path = wordDataList.getAlarmInfo().getAlarmRecord();
		String alarmRecordFileName = "";
		if(StringUtils.isNotBlank(path)) {
			alarmRecordFileName = path.substring(path.lastIndexOf("/") + 1);
		}
		String alarmRecordPath = null;
		if (wordDataList.getAlarmInfo().getAlarmRecord() != null
				&& wordDataList.getAlarmInfo().getAlarmRecord().indexOf("http://") == -1) {
			alarmRecordPath = Global.getConfig("ALARM_INFO_ALARM_RECORD_PATH")
					+ wordDataList.getAlarmInfo().getAlarmRecord();
		} else {
			alarmRecordPath = wordDataList.getAlarmInfo().getAlarmRecord();
		}
		String fileName = null;
		fileName = WordUtils.unloadingFile_New(alarmRecordPath, alarmRecordFileName, targetPath);
		if (StringUtils.isNotBlank(fileName)) {
			WordUtils.addLine(document, "接警录音名称: " + alarmRecordFileName, bfChinese,
					new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		} else {
			WordUtils.addLine(document, "接警录音名称: " + alarmRecordFileName + LOST_DOCUMENT, bfChinese,
					new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		}
		WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "二、处警详情", bfChinese, new Font(bfChinese, 18, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		if (wordDataList.getWordAlarmHandleFilePlanStepActionDataList().size() > 0) {
			for (int i = 0; i < wordDataList.getWordAlarmHandleFilePlanStepActionDataList().size(); i++) {
				WordUtils.addLine(document,
						wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getUserName() + "处警详细记录:",
						bfChinese, new Font(bfChinese, 14, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL),
						Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"处警单编号: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getHandleCode(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"任务描 述: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getTask(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"目的地经度: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getDestinyX(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"目的地纬度: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getDestinyY(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"派单时间: " + WordUtils.DateUtils(
								wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getDispatchTime()),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"接单时间: " + WordUtils.DateUtils(
								wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getReceiveTime()),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"到达时间: " + WordUtils.DateUtils(
								wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getArriveTime()),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"处置完成时间: " + WordUtils.DateUtils(
								wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getFinishTime()),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"接单时经度: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getReceiveX(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"接单时纬度: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getReceiveY(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"到达现场时经度: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getArriveX(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"到达现场时纬度: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getArriveY(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"处警完成时经度: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getFinishX(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"处警完成时纬度: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getFinishY(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"反馈信息: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getHandleResult(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"附件类型: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i)
								.getHandleFileType(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				String name = wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getHandleFileName();
				if (StringUtils.isNotBlank(
						wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getHandleFilePath())) {
					if ("0".equals(
							wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getHandleFileType())) {
						// 图片
						WordUtils.addLine(document,"附件名称: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i)
								.getHandleFileName(),bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
						File f=new File(Global.getConfig("APP_ALARM_HANDLE_FILE_PATH") + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getHandleFilePath());
						if(f.exists()) {
							document.add(WordUtils.ONEPNG(Global.getConfig("APP_ALARM_HANDLE_FILE_PATH") + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getHandleFilePath()));
							WordUtils.addLine(document, "图", bfChinese, new Font(bfChinese, 14, Font.BOLD),Element.ALIGN_CENTER);
						}else {
							WordUtils.addLine(document, "《该图片已丢失！》", bfChinese, new Font(bfChinese, 14, Font.BOLD),Element.ALIGN_CENTER);
						}
					} else {
						// 音视频下载
						if (wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getHandleFilePath().indexOf("http://") == -1) {
							path = Global.getConfig("APP_ALARM_HANDLE_FILE_PATH") + wordDataList
									.getWordAlarmHandleFilePlanStepActionDataList().get(i).getHandleFilePath();
						} else {
							path = wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i)
									.getHandleFilePath();
						}
						fileName = WordUtils.unloadingFile_New(path, name, targetPath);
						if (StringUtils.isNotBlank(fileName)) {
							WordUtils.addLine(document,
									"附件名称: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i)
											.getHandleFileName(),
									bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
						} else {
							WordUtils.addLine(document,
									"附件名称: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i)
											.getHandleFileName() + LOST_DOCUMENT,
									bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
						}
					}
				}
				WordUtils.addLine(document,
						"预案名称: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getPlanName(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"警情级 别: " + DictUtils.getDictLabel(
								wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getPlanIsImportant(),
								"bph_plan_alarm_level", ""),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"警情类型: " + DictUtils.getDictLabel(
								wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getPlanTypeCode(),
								"bph_alarm_info_typecode", ""),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"预案描述: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getPlanContent(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"步骤名称: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getStepName(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"步骤描述: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getStepContent(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"动作名称: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getActionName(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"动作标题: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getActionTitle(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"动作类型: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i).getStepContent(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"动作描述: " + wordDataList.getWordAlarmHandleFilePlanStepActionDataList().get(i)
								.getActionContent(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL),
						Element.ALIGN_JUSTIFIED);
			}
		}
		WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "三、警情操作记录详细", bfChinese, new Font(bfChinese, 18, Font.NORMAL),
				Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
		if (wordDataList.getAlarmHandleLogList().size() > 0) {
			for (int i = 0; i < wordDataList.getAlarmHandleLogList().size(); i++) {
				WordUtils.addLine(document, "操作人员: " + wordDataList.getAlarmHandleLogList().get(i).getUser().getName(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document,
						"操作时 间: " + WordUtils.DateUtils(wordDataList.getAlarmHandleLogList().get(i).getOperateTime()),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document, "操 作描述: " + wordDataList.getAlarmHandleLogList().get(i).getOperateDesc(),
						bfChinese, new Font(bfChinese, 11, Font.NORMAL), Element.ALIGN_JUSTIFIED);
				WordUtils.addLine(document, "", bfChinese, new Font(bfChinese, 11, Font.NORMAL),
						Element.ALIGN_JUSTIFIED);
			}
		}
	}

}
