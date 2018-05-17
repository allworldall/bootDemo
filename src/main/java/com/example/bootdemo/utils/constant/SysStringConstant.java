package com.example.bootdemo.utils.constant;

public class SysStringConstant {

    //**********    业务中需要用到的一些常量  *********
    public static final String DEL_SEND_MESSAGE_SUCCESS        = "success";

    public static final String CHARSET = "UTF-8";

    public static final String SIGN_ALGORITHMS256 = "SHA256WithRSA";

    public static final String DEFAULT_RSA_ALGORITHM="SHA1WithRSA";

    public static final String KEY_ALGORITHM = "RSA";

    public static final String PUBLIC_KEY = "RSAPublicKey";

    public static final String PRIVATE_KEY = "RSAPrivateKey";


    //配置相关的key
    // ** begin* 短信验证相关 ****
    public static final String SEND_MESSAGE_URL           = "phone.messege.url";           //短信服务商url
    public static final String SEND_MESSAGE_ACOUNT        = "phone.messege.account";       //账号
    public static final String SEND_MESSAGE_PASSWORD      = "phone.messege.password";      //密码
    public static final String SEND_MESSAGE_SIGN_KEY      = "phone.messege.sign";          //签名
    public static final String SEND_MESSAGE_CONTENT       ="phone.messege.content";        //发送短信内容
    public static final String EXPIRE_REDIS_VALIDATE_CODE = "redis.validate.code.expire";  //设置验证码保存在redis里的过期时间
    public static final String LENGTH_VALIDATE_CODE       = "length.validate.code";        //验证码长度
    // ** end*   短信验证相关 ****

    //** begin*  xmlRpc相关的配置
    public static final String INVOKE_XML_RPC_ADDRESS     = "xml_rpc_address"; //xmlrpc 调用地址
    public static final String INVOKE_XML_RPC_KEY         = "xml_rpc_key"; //xmlrpc调用key值
    //** end*   xmlRpc相关的配置

    //**  begin*** 服务器防刷配置
    public static final String PROTECTED_VISIT_EXPIRE_KEY        = "protected_visit_expire";//设置服务器单个IP单个接口的防刷过弃时间，单位：秒
    public static final String PROTECTED_VISIT_COUNT_KEY        = "protected_visit_count";//设置服务器单个IP单个接口的防刷次数
    //**  end*** 服务器防刷配置


    //**********    存redis使，通常将key设置一个前缀 ***********************
    public static final String REDIS_INCR_PREFIX               = "incr_";//设置redis保存增量的前缀

    public static final String PREVENT_CONCURRENT_CHECK_CODE   = "checkPhoneCode_concurrent_";//接口防并发存redis的key前缀

    public static final String REDIS_MESSGAGE_PREFIX           = "message_";//设置redis保存短信验证码的前缀

    public static final String REDIS_BINDED_PHONE              = "binded_phoneNum_";//存放已绑定的手机号的key前缀

    public static final String REDIS_INVITED_PHONE             = "invited_phoneNum_";//存放已被邀请过的手机号（被邀请者）

    public static final String REDIS_TEMP_KEY                  = "temp_set_key_";//临时使用的redis的key，用于上报通讯录取交集

    public static final String PROTECTED_VISIT_PREFIX          = "protected_visit_";//服务器防刷存redis时 key前缀



    //***********   状态码描述     *******************************
    public static final String DESC_ERROR_PARAM = "param error";

    public static final String DESC_ERROR_SYSTEM = "system error";

    public static final String DESC_ILLEGAL_VISIST = "illegal visit";

    public static final String DESC_QUERY_ACCOUNT = "cannot query account";

    public static final String DESC_ERROR_SIGN = "error sign";
}
