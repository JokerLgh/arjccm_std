package com.arjjs.ccm.tool;

import java.util.ArrayList;
import java.util.Date;

//import com.arjjs.ccm.modules.plm.equapply.entity.PlmEquApplyDetail;
//import com.arjjs.ccm.modules.plm.storage.entity.PlmEquipment;
import com.arjjs.ccm.modules.sys.entity.Office;
import com.arjjs.ccm.modules.sys.entity.User;

/**
 * 公共类-静态常量类
 * 
 * @author dongqikai
 *
 */
public class PlmTypes {

	public static final String TRAVEL_APPLY_ID = "30e5f3cd63d94d7e9315f008558792f3"; // 出差申请配置id
	public static final String OFFICIAL_DECUMENT_SEND = "d6bc75b3029e4ec39b65189aa69a51a8"; // 发文申请配置id
	public static final String OFFICIAL_DECUMENT_RECEIVE = "118eb79c9d0a47cca32775ef03a8d909"; // 收文申请配置id
	public static final String LEAVE_APPLY = "cb7428a723c64c04b42eeefb28f1c9af"; // 请假申请配置id
	public static final String WORKOVERTIME_APPLY = "cb7428a723c64c04b42eeefb28f1c9aj"; // JIABAN申请配置id
	public static final String BORROW_MONEY = "a148ded2c52744d2b08416d21336ba6b"; // 借款申请配置id
	public static final String REIMBURSEMENT_APPLY = "1b4d8381488144888a11de17c72837fa"; // 报销申请配置id
	public static final String CAR_APPLY_REPAIR_ID = "528c78cebcf34bc6b799b0450ba85ae3"; // 车辆维修保养申请配置id
	public static final String CAR_APPLY_USE_ID = "4ad34ff4722c439089236d5d92051987"; // 用车申请配置id
	public static final String CAR_APPLY_SCRAP_ID = "5cfdce0f8c8a462eae1e7976a2829788"; // 车辆报废申请配置id
	public static final String CAR_APPLY_BUY_ID = "7f4d005ec04144dfb7e337321d6deafb"; // 车辆报废申请配置id
	public static final String EQU_APPLY_RECEIVE = "bb4edda179724b0a97fa718e8b77d8c7"; // 装备领用申请
	public static final String EQU_APPLY_MAINTENANCE = "00661906d592477fa8f0ab6a13b82356";// 装备维修保养申请
	public static final String EQU_APPLY_SCRAP = "e14ed44fec704a99baadd383a70c2fc7";// 装备报废申请
	public static final String ROOM_APPLY_MEETING_ID = "8912d72a0eb5479e89307563443312ef";//会议申请
	public static final String ROOM_APPLY_RECEPTION_ID= "4788a53c8fa04177af05e8fa818d1c99";//接待申请
	public static final String CONTRACT_SIGN_ID = "0ef2cd1f4dc34315b9d6b0a6dd5f8b98";//合同会签申请
	public static final String PURCHASE_APPLY_ID= "220a0fb6f2554a6c9ddbf1e9530e1295";//政府项目采购申请申请
	public static final String PURCHASE_DECLARE_ID= "220a0fb6f2554a6c9ddbf1e9530e45A2";//采购申请
	public static final String PLM_OPINION = "d06023251f234aab9890827118c5eee1";//建议意见
	public static final String ALLOT_APPLY = "7229b56dccc145fa80d8434d4eec0f15";//资产内部调拨申请
	
	
	// 文化中心分类
	public static final String NEWS_TYPE = "01"; // 新闻中心
	public static final String ARTICLE_TYPE = "03"; // 电子公告
	public static final String CULTURE_TYPE = "02"; // 文化建设
	public static final String SEX_MAN = "0";//男
	public static final String SEX_WOMAN = "1";//女
	public static final String KNOW_POLICY = "1";//规章制度
	public static final String POLICY_ACT = "2";//政策法规
	public static final String EQU_APPLY = "1";//装备申请
	public static final String EQU_SCRAP = "2";//报废申请发
	public static final String EQU_MAINTENANCE = "3";//维修保养申请
	 // 通用分类
	public static final String TYPE = "1"; 
	public static final String IS_SUP_TYPE = "0"; // 是否督办
	
	
	
	public static final ArrayList<String> PUBLIC_COLUMNS = new ArrayList<String>() {
		{
			add("id");
			add("procInsId");
			add("extend1");
			add("extend2");
			add("createBy");
			add("createDate");
			add("updateBy");
			add("updateDate");
			add("remarks");
			add("delFlag");
			add("detail");
			add("equipment");
		} // 实体类中的公共字段(流程配置部分使用)
	};
	public static final ArrayList<Class<?>> CLASS_TYPE = new ArrayList<Class<?>>() {
		{
			add(User.class);
			add(Office.class);
			add(Date.class);
			//add(PlmEquApplyDetail.class);
			//add(PlmEquipment.class);

			
			
		}
	};
	public static final int OLD_AGE = 65;
}
