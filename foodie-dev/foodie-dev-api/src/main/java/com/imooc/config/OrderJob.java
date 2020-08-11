package com.imooc.config;

import com.imooc.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020-08-11-16:59
 */
@Component
public class OrderJob {

    @Scheduled(cron = "0/3 * * * * ? ")
    public void autoCloseOrder(){
        System.out.println("执行定时任务，当前时间为："
        + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }

}
