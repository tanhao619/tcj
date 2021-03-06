package com.stylefeng.guns.common.exception;

/**
 * @Description 所有业务异常的枚举
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum {

	/**
	 * 字典
	 */
	DICT_EXISTED(400,"字典已经存在"),
	ERROR_CREATE_DICT(500,"创建字典失败"),
	ERROR_WRAPPER_FIELD(500,"包装字典属性失败"),


	/**
	 * 文件上传
	 */
	FILE_READING_ERROR(400,"FILE_READING_ERROR!"),
	FILE_NOT_FOUND(400,"FILE_NOT_FOUND!"),
	UPLOAD_ERROR(500,"上传图片出错"),

	/**
	 * 权限和数据问题
	 */
	DB_RESOURCE_NULL(400,"数据库中没有该资源"),
	NO_PERMITION(405, "权限异常"),
	REQUEST_INVALIDATE(400,"请求数据格式不正确"),
	INVALID_KAPTCHA(400,"验证码不正确"),

	CANT_DELETE_ADMIN(600,"不能删除超级管理员"),
	CANT_DELETE_SWGD(600,"不能删除收文/归档科员"),
	CANT_DELETE_BGSZR(600,"不能删除办公室主任"),

	CANT_FREEZE_ADMIN(600,"不能冻结超级管理员"),
	CANT_FREEZE_SWGD(600,"不能冻结收文/归档科员"),
	CANT_FREEZE_BGSZR(600,"不能冻结办公室主任"),

	CANT_CHANGE_ADMIN(600,"不能修改超级管理员角色"),
	CANT_CHANGE_SWGD(600,"不能修改收文/归档科员角色"),
	CANT_CHANGE_BGSZR(600,"不能修改办公室主任角色"),

	CANT_MIX_ALLOTMENT_ADMIN(600,"超级管理员不能与其他角色混合分配!"),

	/**
	 * 账户问题
	 */
	/*USER_ALREADY_REG(401,"该用户已经注册"),*/
	USER_ALREADY_REG(401,"该用户已经存在！"),
	NO_THIS_USER(400,"没有此用户"),
	USER_NOT_EXISTED(400, "没有此用户"),
	ACCOUNT_FREEZED(401, "账号被冻结"),
	OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
	TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),
	NEW_PWD_CANT_SAME_TO_OLD_PWD(405, "新密码不能与原密码相同"),
	PWD_CANt_BELOW_SIX(401,"密码不能小于6位"),

	/**
	 * 错误的请求
	 */
	MENU_PCODE_COINCIDENCE(400,"菜单编号和副编号不能一致"),
	EXISTED_THE_MENU(400,"菜单编号重复，不能添加"),
	DICT_MUST_BE_NUMBER(400,"字典的值必须为数字"),
	REQUEST_NULL(400, "请求有错误"),
	SESSION_TIMEOUT(400, "会话超时"),
	SERVER_ERROR(500, "服务器异常"),

	/*
		业务逻辑状态码
 	*/
	REPEAT_ENTITY_ERROR(501, "标题重复"),
	PATAM_ERROR(502, "搜索条件有误"),
	TOO_LONG(503, "标识超过了限定长度"),
	PARAM_ERROR(504, "传入参数有误");

	BizExceptionEnum(int code, String message) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
	}
	
	BizExceptionEnum(int code, String message,String urlPath) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
		this.urlPath = urlPath;
	}

	private int friendlyCode;

	private String friendlyMsg;
	
	private String urlPath;

	public int getCode() {
		return friendlyCode;
	}

	public void setCode(int code) {
		this.friendlyCode = code;
	}

	public String getMessage() {
		return friendlyMsg;
	}

	public void setMessage(String message) {
		this.friendlyMsg = message;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

}
