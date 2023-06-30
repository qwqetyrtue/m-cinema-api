package cn.hnist.sharo.mcinema.core.model;

/**
 * <h3> resCode 说明</h3>
 * <ul>
 *      <li> 0，成功；</li>
 *      <li> 4xx，前端错误</li>
 *      <ul>
 *          <li> 401，参数错误，即前端没有传递后端需要的参数；</li>
 *          <li> 402，参数值错误，即前端传递的参数值不符合后端接收范围。</li>
 *      </ul>
 * <li> 5xx，后端错误，除501外</li>
 *      <ul>
 *          <li> 501，验证失败，即后端要求用户登录；</li>
 *          <li> 502，系统内部错误，即没有合适命名的后端内部错误；</li>
 *          <li> 503，业务不支持，即后端虽然定义了接口，但是还没有实现功能；</li>
 *          <li> 504，更新数据失效，即后端采用了乐观锁更新，而并发更新时存在数据更新失效；</li>
 *          <li> 505，更新数据失败，即后端数据库更新失败（正常情况应该更新成功）。</li>
 *      </ul>
 * <li> 6xx，管理后端业务错误码，</li>
 * <li> 7xx，管理后台后端业务错误码，</li>
 * </ul>
 **/
public enum ErrorEnum {
    MISSING_CALCULATE_OBJECT(350,"计算对象缺失"),

    MISSING_REQUEST_BODY(400, "请求体缺失"),
    MISSING_REQUEST_PARAMS(401, "请求参数缺失"),

    WRONG_PARAMS_FORMAT(402, "传入参数格式错误"),

    WRONG_PARAMS_VALUE(403, "传入参数值错误"),

    METHOD_NOT_ALLOWED(405, "请求方法不支持"),

    OVERSIZE_UPLOAD(413, "文件大小超出"),
    FAILED_UPLOAD(414, "文件上传失败"),
    FAILED_LOAD(415, "文件读取失败"),
    FAILED_DELETED(416, "文件删除失败"),
    FAILED_FETCH(417, "未找到此资源"),
    ILLEGAL_URL(417, "URL路径错误"),

    CONSTRAINT_EXCEPTION(450, "数据库约束错误"),
    DATA_NOT_FOUND(451, "未查询到数据库数据"),
    DATA_IS_DELETED(452, "对象已经被删除"),
    ILLEGAL_OPERATE(453, "不允许的数据库操作"),
    INVALID_OPERATE(454, "无效的数据库操作"),
    FAILED_AUTHENTICATED_OPERATE(455, "数据库操作未通过验证"),
    FAILED_TRANSACTION(456,"数据库事务操作失败"),


    WITHOUT_AUTHENTICATED(501, "访问未经验证"),

    WITHOUT_AUTHORIZED(502, "访问未经授权"),

    INVALID_TOKEN(503, "验证失败:无效的token"),
    TIMEOUT_TOKEN(504, "验证失败:超时的token"),


    INVALID_ACCOUNT(550, "无效的用户名或密码"),
    INVALID_ACCOUNT_NAME(551, "无效的用户名"),
    INVALID_ACCOUNT_PASSWORD(552, "无效的密码"),

    MISSING_GENERATE_CAPTCHA(601, "验证码没有生成"),
    EXPIRED_CAPTCHA(602, "验证码已过期"),
    WRONG_CAPTCHA(603, "验证码错误"),
    DUPLICATE_GENERATE_CAPTCHA(604, "重复创建验证码"),

    UNKNOWN_EXCEPTION(-1, "未知错误"),
    UNDEPLOY_SERVICES(-2, "服务未部署");


    private final int code;
    private final String errorMsg;


    ErrorEnum(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public static ErrorEnum parse(int code) {
        try {
            return ErrorEnum.valueOf("Error" + code);
        } catch (IllegalArgumentException e) {
            return ErrorEnum.UNKNOWN_EXCEPTION;
        }
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}