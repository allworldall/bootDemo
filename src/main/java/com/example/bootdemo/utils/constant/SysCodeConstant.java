package com.example.bootdemo.utils.constant;

public class SysCodeConstant {


    //**********       状态码    ********
    public static final int					DEAL_SUCCESS							= 1;		 //执行成功状态吗
    public static final int					ERROR_SYSTEM					        = -200;		 //系统异常
    public static final int					HTTP_EXCEPION					        = -201;		 //http请求异常
    public static final int                 ERROR_PARAM                             = -1001;     //请求参数格式错误
    public static final int                 ERROR_QUERY_ACCOUNT                     = -1002;     //未查到用户
    public static final int                 ERROR_SEND_PHONE_MESSAGE                = -1003;    //请求服务商发送验证码失败
    public static final int                 ERROR_PROPERTY_CONFIG                   = -1007;    //参数未配置
    public static final int                 ERROR_CONFIG_VALIDATE_LENGTH            = -1008;    //验证码长度未配置
    public static final int                 ERROR_CONFIG_VALIDATE_EXPIRED           = -1009;    //验证码过期时间未配置
    public static final int                 ERROR_CONCURRENT_RQUEST                 = -1010;    //验证码有效时间内有重复请求，包括并发请求
    public static final int                 ERROR_SIGN                              = -1011;    //验签失败
    public static final int                 ERROR_INVITED_REPEAT                    = -1012;    //该用户已经被邀请
    public static final int                 ERROR_ENCAPSULATE_PARAM                 = -1013;    //参数封装错误（基本不会）
    public static final int                 ERROR_PHONE_NOT_BIND                    = -1014;    //账号未绑定
    public static final int                 INVOKE_XML_RPC_ERROR                    = -1200;    //xmlRpc错误
    public static final int                 ERROR_OTHER                             = -1500;    //其它错误


    public static final int                 ERROR_ILLEGAL_VISIST                    = -1100;    //不合法的调用（包括刷数据）
}
