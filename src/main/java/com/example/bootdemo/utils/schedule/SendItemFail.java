package com.example.bootdemo.utils.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Vector;

/**
 * 此类用于
 */
@Component
public class SendItemFail {


    @Scheduled(cron="0 0 1/1 * * ?")  //每一小时重发一次
    public void doTask() {


        try{


        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
