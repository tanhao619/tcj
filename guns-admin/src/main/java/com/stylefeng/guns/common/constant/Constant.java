package com.stylefeng.guns.common.constant;

/**
 * 模块中的常量
 * @author sijianmeng
 */
public class Constant {
    //分隔符
    public static final String SEPARATOR = ",";

    //时间格式
    public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";


    //请求参数
    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String RESULT = "result";
    public static final String DATA = "data";

    public static final String OK = "OK";
    public static final String ERROR = "ERROR";

    public static final String OK_MSG = "操作成功";
    public static final String ERROR_MGS = "操作失败";
    public static final String EXPORT_ERROR = "导出失败";
    public static final String ADD_ERROR = "新增失败";
    public static final String UPDATE_ERROR = "修改失败";
    public static final String EXPORT_OK = "导出成功";
    public static final String DEL_ERROR = "删除失败";
    public static final String ADD_OK = "新增成功";
    public static final String UPDATE_OK = "修改成功";
    public static final String DEL_OK = "删除成功";
    public static final String FLAG_ERROR= "页面标识错误";
    public static final String START_OK= "启动成功";
    public static final String STOP_OK= "停止成功";
    public static final String START_ERROR= "启动失败";

    /**
     * 标识
     */
    //供应商
    public static final String PROVIDER_PRE = "P";
    public static final String PROVIDER_SUF = "00001";
    //一级标识
    public static final String CATE_FIRST_PRE = "F";
    public static final String CATE_FIRST_SUF = "00001";
    //二级标识
    public static final String CATE_SECOND_PRE = "S";
    public static final String CATE_SECOND_SUF = "00001";
    //对象
    public static final String ACO_PRE = "AC";
    public static final String ACO_SUF = "00001";
    //对象
    public static final String SET_PRE = "D";
    public static final String SET_SUF = "00001";
    //对象
    public static final String TASK_PRE = "_TASK";
    public static final String TASK_SUF = "1";

    public static final String URI_PRE = "daas://";

    /**
     * 模块
     */
    //Provider
    public static final String PRO_FILE_NAME = "供应商";
    public static final String PRO_COL_NAMES = "供应商名称,供应商标识,创建时间,供应商描述";
    public static final String PRO_COL_KEYS = "proTitle,proCode,proCTime,proDesc";

    //Category
    public static final String CAT_FILE_NAME = "一级分类";
    public static final String CAT_COL_NAMES = "一级分类名称,一级分类标识,创建人,二级分类数,一级分类描述";
    public static final String CAT_COL_KEYS = "cateTitle,cateCode,cateCreater,cateCount,cateDesc";

    //Objects
    public static final String OBJ_FILE_NAME = "接入对象";
    public static final String OBJ_FTP_COL_NAMES = "对象名称,对象标识,供应商名称,FTP地址,FTP端口,用户名,根目录";
    public static final String OBJ_API_COL_NAMES = "对象名称,对象标识,供应商名称,链接地址,获取规则";
    public static final String OBJ_FTP_COL_KEYS = "acoTitle,acoCode,proCode,ip,port,user,rootDataAcquisA";
    public static final String OBJ_API_COL_KEYS = "acoTitle,acoCode,proCode,url,way";

    //warnning
    public static final String WARN_OVER_TIME_COUNTS = "overtimeCounts";
    public static final String WARN_ATYPICAL_COUNTS = "atypicalCounts";
    public static final String WARN_OUTSIDE_COUNTS = "outsideCounts";
    public static final String WARN_REPEAT_FILES_COUNTS = "repeatFilesCounts";

    //qclog
    public static final String QC_LOG_FILE_NAME = "质检入库日志";
    public static final String QC_LOG_COL_NAMES = "数据集名称,分类名称,文件名,执行人,记录,创建时间";
    public static final String QC_LOG_COL_KEYS = "resTitle,cateTitle,fileName,executor,message,createTime";

    //warnning导出cvs
    public static final String WARNNING_FILE_NAME = "告警";
    public static final String WARNNING_COL_NAMES = "任务标识,数据集名称,告警类型,接入类型,更新周期,更新数量要求,更新定时时间,告警时间";
    public static final String WARNNING_COL_KEYS = "taskId,resTitle,warnningType,acoType,upCycle,upNum,upHour,createTime";

    //dataset
    public static final String DATASET_AUTO_PASS_SUCCESS = "自动过检成功";

    public static final String API_RESPONSE_PARAMS_CODE = "code";
    public static final String API_RESPONSE_PARAMS_MSG = "message";
    public static final String API_RESPONSE_PARAMS_RESULT = "result";
    public static final String API_RESPONSE_PARAMS_DATAS = "datas";
    public static final String API_RESPONSE_PARAMS_DATA = "data";
    public static final String API_RESPONSE_PARAMS_TOTALCOUNTS = "totalCounts";

    public static final Integer API_RESULT_CODE_SUCCESS = 200;
    public static final String API_RESULT_CODE_MSG_SUCCESS = "操作成功";


    //外部接口
    public static final String OUTER_API_TASK_ADD = "http://192.168.0.147:18010/api/v1/daas/access/core/tasks";
    public static final String OUTER_API_TASK_DEL = "http://192.168.0.147:18010/api/v1/daas/access/core/tasks/";
    public static final String OUTER_API_TASK_START = "http://192.168.0.147:18010/v1/daas/access/core/tasks/";

    // 文件
    public static final String FILE_DEl_ERROR = "删除失败"; //"删除失败，请检查文件服务器路径"
    public static final String FAIL_UPLOAD = "上传失败";    //"上传失败，请检查文件服务器路径"

    // 载体
    public static final String ZATE_LAND_PROPERTY_TYEP = "type";
    public static final String ZATE_LAND_PROPERTY_ZATEID = "zateId";
    // 载体导出
    public static final String ZATE_TYPE = "zateType";
    public static final String Z_TYPE = "type";
    public static final String ZATE_ALL = "all";
    public static final String ZATE_LAND_EXPORT_NAME = "土地类载体资源";
    public static final String ZATE_BUILDING_EXPORT_NAME = "楼宇或标准厂房载体资源";
    public static final String ZATE_LAND_TYPE = "T";
    public static final String ZATE_BUILDING_TYPE = "L";


    // 权限
    public static final String INFO_CENTER = "信息服务中心";
    public static final String DEPT_OFFICE = "办公室";
    public static final String ROLE_KEYUAN = "1";
    public static final String ROLE_KEZHANG = "2";
    public static final String ROLE_FUJUZHANG = "3";
    public static final String ROLE_JUZHANG = "4";
    public static final String DEPT_JUZHANGBGS = "局长办公室";
    //project
    public static final String CHECK_RESULT = "检查完成";

    // OA管理
    public static final String OA_ADD_ERROR = "发起审批失败";
    public static final String OA_QINGJIA_TYPE = "1";
    public static final String OA_SHOWWEN_TYPE = "8";
    public static final String OA_FAWEN_TYPE = "7";

    public static final String OA_STATUS_CHEXIAO = "6";

    public static final String SWGD = "swgd";
    public static final String BGSZR = "bgszr";

    // 项目
    public static final String NOMALPROJECT_FOLTYPE_DAIFENPEI = "1";
    public static final String NOMALPROJECT_FOLTYPE_YIFENPEI = "2";

    public static final String BIGPROJECT_FOLTYPE_DAIFENPEI = "1";
    public static final String BIGPROJECT_FOLTYPE_YIFENPEI = "2";

    // zate excel
    public static final String[] ZATE_EXCEL_ALEN = {"备注","编号"};
}
