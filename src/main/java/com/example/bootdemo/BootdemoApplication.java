package com.example.bootdemo;

import com.example.bootdemo.utils.log.LoggerUtil;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class})*/ /*读取数据源组件*/
@EnableCaching
//@EnableEncryptableProperties   /*势力化第三方加密组件*/
@EnableScheduling
public class BootdemoApplication {

	public static void main(String[] args) {

		// 新增注释
		String direct = System.getenv("BOOT_DEMO");
//		String direct = "/Users/panpan/workspace/myproject/config";
		//初始化log4j
		String log4jPath = direct + File.separator + "log4j.properties";
		LoggerUtil.info(BootdemoApplication.class, "log config file path:"+log4jPath);
		if(direct == null) {
			LoggerUtil.error(BootdemoApplication.class, "BOOT_DEMO 环境变量没有配置");
			System.exit(0);
		}
		if(!Files.exists(Paths.get(log4jPath))) {
			LoggerUtil.error(BootdemoApplication.class,"路径："+log4jPath+" log4j配置文件不存在");
			System.exit(0);
		}
		//加载log4j配置文件
		PropertyConfigurator.configure(log4jPath);
		String appPath = direct +File.separator+ "application.properties";
		Properties properties = new Properties();
		try{
			File appFile = new File(appPath);
			properties.load(new FileInputStream(appFile));
			SpringApplication springApplication = new SpringApplication(BootdemoApplication.class);

			springApplication.setDefaultProperties(properties);

			springApplication.run(args);
		}catch (IOException e) {
			LoggerUtil.error(BootdemoApplication.class,"路径：" + appPath + " application配置文件读取异常");
			System.exit(0);
		}


	}
}
