package com.example.bootdemo.utils.DataFormatUtils;


import com.example.bootdemo.utils.log.LoggerUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtils {
	
	/** 
     * ip地址转成long型数字 
     * 将IP地址转化成整数的方法如下： 
     * 1、通过String的split方法按.分隔得到4个长度的数组 
     * 2、通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1 
     * @param strIp 
     * @return 
     */  
    public static long ipToLong(String strIp) {
    	    long result = 0;
    	    try {
    	    	 	  String[]ip = strIp.split("\\.");  
    	          result = (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);  
			} catch (Exception e) {
				LoggerUtil.error(IPUtils.class, "request ip "+strIp+" error:");
				result = ipToLong("127.0.0.1");
			}
    	    return result;
       
    }
    /** 
     * 将十进制整数形式转换成127.0.0.1形式的ip地址 
     * 将整数形式的IP地址转化成字符串的方法如下： 
     * 1、将整数值进行右移位操作（>>>），右移24位，右移时高位补0，得到的数字即为第一段IP。 
     * 2、通过与操作符（&）将整数值的高8位设为0，再右移16位，得到的数字即为第二段IP。 
     * 3、通过与操作符吧整数值的高16位设为0，再右移8位，得到的数字即为第三段IP。 
     * 4、通过与操作符吧整数值的高24位设为0，得到的数字即为第四段IP。 
     * @param longIp 
     * @return 
     */
    public static String longToIP(long longIp) {  
        StringBuffer sb = new StringBuffer("");  
        // 直接右移24位  
        sb.append(String.valueOf((longIp >>> 24)));  
        sb.append(".");  
        // 将高8位置0，然后右移16位  
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));  
        sb.append(".");  
        // 将高16位置0，然后右移8位  
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));  
        sb.append(".");  
        // 将高24位置0  
        sb.append(String.valueOf((longIp & 0x000000FF)));  
        return sb.toString();  
    }
    /**
	 * 获取真实IP
	 * @param request
	 * @return
	 */
	public static String getTrueIP(HttpServletRequest request){
		//因原方式获取到的是nginx转发的服务器Ip，所以将ip设置为nginx配置在Header中的实际用户的值
		String ip="";
		String xRealIp = request.getHeader("X-Real-IP") ;
		String XForwardedIp = request.getHeader("X-Forwarded-For") ;
		if(!StringUtils.isEmpty(XForwardedIp)){
			ip = XForwardedIp.split(",")[0];
		}else if(!StringUtils.isEmpty(xRealIp)){
			ip = xRealIp;
		}else{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 获取用户的UA
	 */
	public static String getUserAgent(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		return userAgent;
	}

	/**
	 * 获取本机IP
	 */
	public static String getLocalIp() {
		String localIp = "127.0.0.1";
		try {
			InetAddress address = InetAddress.getLocalHost();// 获取的是本地的IP地址
			localIp = address.getHostAddress();// 192.168.0.121

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return localIp;
	}
	public static void main(String[] args) {
		System.out.println(ipToLong("127.0.0.1"));
	}
}
