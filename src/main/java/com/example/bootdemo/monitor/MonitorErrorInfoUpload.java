package com.example.bootdemo.monitor;


import com.example.bootdemo.utils.DataFormatUtils.IPUtils;

public class MonitorErrorInfoUpload implements Runnable {

	private String message;
	
	public MonitorErrorInfoUpload(String message){
		this.message = message;
	}
	@Override
	public void run() {
		MonitorConfig.uploadInfo(MonitorConfig.getInvokeUrl(), "unionConsumer("+ IPUtils.getLocalIp()+")", "严重", message, null);
	}

}
