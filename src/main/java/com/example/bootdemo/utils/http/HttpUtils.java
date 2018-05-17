package com.example.bootdemo.utils.http;


import com.example.bootdemo.utils.log.LoggerUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class HttpUtils {

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 * 设置超时时间为2秒
	 */
	public static String sendGet(String url, String param) {
		long begin = System.currentTimeMillis();
		String result = "";
		BufferedReader br = null;
		HttpURLConnection connection = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			connection =(HttpURLConnection) realUrl.openConnection();
			//设置超时时间，单位：毫秒
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(2000);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();
		} catch (Exception e) {
			LoggerUtil.error(HttpUtils.class, "request url=" + url +",param=" + param +",result="+result + ",error info:" + e.toString() + ",time="+(System.currentTimeMillis()-begin));
		}finally {
			LoggerUtil.info(HttpUtils.class, "request url=" + url +",param=" + param +",result="+result + ",time="+(System.currentTimeMillis()-begin));
			if(connection != null) {
				connection.disconnect();
			}
		}
		return result;
	}


	public static String sendPost(String url, Map<String,String> map) {
		URL u = null;
		StringBuffer buffer = new StringBuffer();
		HttpURLConnection conn = null;
		StringBuilder log = new StringBuilder();
		log.append("POST,url:"+url);
		StringBuffer sb = new StringBuffer();
		//拼接请求参数
		if (!map.isEmpty()) {
			for (Map.Entry<String, String> e : map.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
		}
		sb.substring(0, sb.length() - 1);
		log.append(",param:"+sb.toString());
		// 发送数据
		try {
			u = new URL(url);
			//打开和URL的连接
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			//获取HttpURLConnection对象的输出流
			OutputStreamWriter osw = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
			log.append(",result:");
			// 读取数据
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
			br.close();
			log.append(buffer.toString());
			LoggerUtil.info(HttpUtils.class, log.toString());
		} catch (Exception e) {
			LoggerUtil.error(HttpUtils.class, "POST,url:"+url+",error:"+e.toString()+",param:"+sb.toString());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return buffer.toString();
	}
}
