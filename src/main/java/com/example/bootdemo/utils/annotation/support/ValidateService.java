package com.example.bootdemo.utils.annotation.support;



import com.example.bootdemo.utils.DataFormatUtils.RegexUtil;
import com.example.bootdemo.utils.DataFormatUtils.StringUtils;
import com.example.bootdemo.utils.annotation.DataValidate;
import com.example.bootdemo.utils.annotation.RegexType;
import com.example.bootdemo.utils.exceptionUtil.ParamException;

import java.lang.reflect.Field;

/**
 * 注解解析
 */
public class ValidateService {
	
	
	
	public ValidateService(){
		super();
	}
	//解析入口
	public static void valid(Object object) throws ParamException {
		//获取object类型
		Class<? extends Object> clazz = object.getClass();
		//获取该类型声明的成员
		Field[] fields = clazz.getDeclaredFields();
		//遍历属性
		for (Field field:fields) {
			//对于private私有化的成员变量，通过setAccessible来修改器访问权限
			field.setAccessible(true);
			validate(field,object);
			//重新设置会私有权限
			field.setAccessible(false);
		}
	}
	//验证服务
	public static void validate(Field field, Object object) throws ParamException {
		
		String description;
		Object value;
		
		//获取对象的成员的注解信息
		DataValidate dataValidate = field.getAnnotation(DataValidate.class);
		try {
			value		 = field.get(object);
		} catch (IllegalAccessException e) {
			//为使InviteActivityController的checkAddrList能简介的使用java8特性，将此异常改成参数异常抛出
			throw new ParamException(field.getName() + " variable not private");
		}

		if(dataValidate == null){
			return;
		}
		description = dataValidate.description().equals("") ? field.getName() : dataValidate.description();
		
		/********************解析注解********************/
		if(!dataValidate.nullable()){
			if(value == null || StringUtils.isBlank(value.toString())){
				throw new ParamException(description + field.getName() + " not null");
			}
		}else{
			return;
		}
		if(value.toString().length() > dataValidate.maxLength() && dataValidate.maxLength() != 0){
			throw new ParamException(description + field.getName() + " The length can not exceed " + dataValidate.maxLength());
		}
		
		if(value.toString().length() < dataValidate.minLength() && dataValidate.minLength() != 0){
			throw new ParamException(description + field.getName() + " The length can not be less than " + dataValidate.minLength());
		}

		if(value.toString().length() != dataValidate.length() && dataValidate.length() != 0){
			throw new ParamException(description + field.getName() + " The length not equals to " + dataValidate.length());
		}

		if(dataValidate.regexType() != RegexType.NONE){
			switch (dataValidate.regexType()) {
			case NONE:
				break;
			case SPECIALCHAR:
				if(!RegexUtil.hasSpecialChar(value.toString())){
					throw new ParamException(description + field.getName() + " No special characters can be contained");
				}
				break;
			case CHINESE:
				if(!RegexUtil.isChinese2(value.toString())){
					throw new ParamException(description + field.getName() + " Cannot contain Chinese characters");
				}
				break;
			case EMAIL:
				if(!RegexUtil.isEmail(value.toString())){
					throw new ParamException(description + field.getName() + " Incorrectly formatting");
				}
				break;
			case IP:
				if(!RegexUtil.isIp(value.toString())){
					throw new ParamException(description + field.getName() + " Incorrectly formatting");
				}
				break;
			case NUMBER:
				if(!RegexUtil.isNumber(value.toString())){
					throw new ParamException(description + field.getName() + " Incorrectly formatting");
				}
				break;
			case NUMBER1:
				if(!RegexUtil.isNumber(value.toString())){
					throw new ParamException(description + field.getName() + " Incorrectly formatting");
				}
				break;
			case PHONENUMBER:
				if(!RegexUtil.isPhoneNo(value.toString())){
					throw new ParamException(description + field.getName() + " Incorrectly formatting");
				}
				break;
			default:
				break;
			}
		}
		
		if(!dataValidate.regexExpression().equals("")){
			if(value.toString().matches(dataValidate.regexExpression())){
				throw new ParamException(description + field.getName() + " Incorrectly formatting");
			}
		}
	}
}
