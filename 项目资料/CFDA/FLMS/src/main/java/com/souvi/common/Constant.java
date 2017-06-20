package com.souvi.common;

public class Constant {
	/**
	 * 登录用户的session名称
	 */
	public static final String USER_SESSION_NAME = "LOGINUSERSESSION";
	public static final String USER_SESSION_ROLE = "LOGINUSERSEROLE";
	/** 是否删除 **/
	public static final String ISDELETE_Y = "Y";
	public static final String ISDELETE_N = "N";
	/** 状态 **/
	public static final String STATUS_Y = "Y";
	public static final String STATUS_N = "N";
	/** 定义树根节点ID为-1 **/
	public static final String TREE_MAIN_NODE_ID = "-1";
	public static final String TREE_MAIN_NODE_NAME = "功能菜单";
	/**审批操作状态**/
	public static final String AuditType_Submit = "提交";		//提交
	public static final String AuditType_ApprovalY = "审批通过";	//审批通过
	public static final String AuditType_NoApprovalN = "审批不通过";	//审批不通过
	/**审批状态 主键Id**/
	public static final String AuditType_New = "AuditType1";//新建
	public static final String AuditType_InApproval = "AuditType2";//审批中
	public static final String AuditType_Approval = "AuditType3";//审批通过
	public static final String AuditType_NotApproval = "AuditType4";//审批不通过
	/**检查确认状态 主键Id**/
	public static final String CheckConfirm_NotChecked = "CheckType1";//未检查
	public static final String CheckConfirm_InCheck = "CheckType2";//检查中
	public static final String CheckConfirm_Confirmed = "CheckType3";//已确认
	public static final String CheckConfirm_Reject = "CheckType4";//驳回
	/**公司类型 主键Id**/
	public static final String CompanyType_Customer = "CompanyType1";//委托方客户
	public static final String CompanyType_Shipper = "CompanyType2";//委托客户供应商
	public static final String CompanyType_ReceivingParty  = "CompanyType3";//委托客户销售客户
	public static final String CompanyType_Other = "CompanyType4";//第三方仓储物流服务商
	/**数据来源 主键Id **/
	public static final String DataSource_Web = "DataSource1";//手动创建
	public static final String DataSource_EDI = "DataSource2";//EDI
	/**数据状态 主键Id **/
	public static final String DataStatus_Enable = "DataStatus1";//启用
	public static final String DataStatus_Disable = "DataStatus2";//禁用
	public static final String DataStatus_Locking = "DataStatus3";//锁定
	/**经营方式 主键Id **/
	public static final String ManagementMode_Production  = "OperationType1";//生产
	public static final String ManagementMode_Wholesale = "OperationType2";//批发
	public static final String ManagementMode_RetailChain  = "OperationType3";//零售连锁
	public static final String ManagementMode_Retail = "OperationType4";//零售
	public static final String ManagementMode_MedicalInstitution = "OperationType5";//医疗机构
	/**角色**/
	public static final String Role_FirstAduit  = "0c4a476ddc56428d81229fb392fba53b";//一级审批人
	public static final String Role_SecondAduit = "a7a20685b4554041bafde6a5fffd1609";//二级审批人
	/**审批阶段**/
	public static final int AuditLog_Submit  = 0;//提交人
	public static final int AuditLog_FirstAduit  = 1;//一级审批人
	public static final int AuditLog_SecondAduit = 2;//二级审批人
	/**邮件 发送通道**/
	public static final String Mail_Company_Audit  = "CompanyAudit";//公司审批
	public static final String Mail_Company_EDI_Add  = "CompanyEDIAdd";//EDI公司添加
	public static final String Mail_Product_Audit = "ProductAudit";//产品审批
	public static final String Mail_Product_EDI_Add  = "ProductEDIAdd";//EDI产品添加
	public static final String Mail_Product_EDI_Update  = "ProductEDIUpdate";//产品更新
	public static final String Mail_ProductLicense_EDI_Add = "ProductLicenseEDIAdd";//EDI产品证照添加
	public static final String Mail_ProductLicense_EDI_Update = "ProductLicenseEDIUpdate";//EDI产品证照更新
	public static final String Mail_CompanyLicense_EDI_Add = "CompanyLicenseEDIAdd";//EDI公司证照添加
	public static final String Mail_CompanyLicense_EDI_Update = "CompanyLicenseEDIUpdate";//EDI公司证照更新
	/**邮件发送状态**/
	public static final String Mail_SendFlag_New  = "New";//未发送
	public static final String Mail_SendFlag_Success  = "Success";//发送成功
	public static final String Mail_SendFlag_Error = "Error";//发送失败
}
