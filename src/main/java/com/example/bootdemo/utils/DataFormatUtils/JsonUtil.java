package com.example.bootdemo.utils.DataFormatUtils;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.List;

/**
 * Json类工具
 */
public final class JsonUtil {
          
   /**
    * Object 转换为json
    * @param Object obj
    * 				obj对象
    * @return String json
    */
   public static String convertBeanToJson(Object obj){
	   return obj == null ? null : new Gson().toJson(obj); 
   }
   /**
    * gson进行json转换对象
    * @param String json
    * 				json格式数据
    * @param Class  clazz
    * 				需要转换的对象
    */
   public static <T> T convertJsonToBean(String json, Class<? extends T> clazz){
	   Gson gson = new Gson();
	   return  json == null ? null : gson.fromJson(json, clazz); 
   }

    /**
     * list集合转换成Json
     * @param list
     * @return
     */
   public static String listToJson(List list) {
       String jsonString = JSON.toJSONString(list);
       return jsonString;
   }


   public static <T>  List<T> jsonToList (String jsonString, Class clazz) {
       List<T> list = JSON.parseArray(jsonString, clazz);
       return list;
   }
}
