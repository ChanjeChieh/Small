package com.small.task;

import com.small.common.Const;
import com.small.common.RedisShardedPool;
import com.small.common.RedissonManager;
import com.small.service.IOrderService;
import com.small.util.PropertiesUtil;
import com.small.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.LongLiteral;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by skdwj on 2020/4/7.
 */

@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private RedissonManager redissonManager;

//    @Scheduled(cron = "0 */1 * * * ?")
    public void closeTaskV1(){
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
//        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

//    @Scheduled(cron = "0 */1 * * * ?")
    public void closeTaskV2(){
        log.info("关闭订单定时任务启动");
        long lockTimeOut = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000")) ;

        Long setnxResult = RedisShardedPoolUtil.setnx(
                Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,
                String.valueOf(System.currentTimeMillis()+lockTimeOut));
        if(setnxResult != null && setnxResult == 1){
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }else{
            log.info("没有获得分布式锁:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }

        log.info("关闭订单定时任务结束");
    }

//    @Scheduled(cron = "0 */1 * * * ?")
    public void closeTaskV3(){
        log.info("关闭订单定时任务启动");
        long lockTimeOut = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        Long setnxResult = RedisShardedPoolUtil.setnx(
                Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,
                String.valueOf(System.currentTimeMillis()+lockTimeOut));
        if(setnxResult != null && setnxResult.intValue() == 1){
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }else{
            //未获取到锁，继续判断，通过时间戳判断是否可以重置并获取到锁
            String lockValueStr = RedisShardedPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            if(lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)){

                String getSetResult = RedisShardedPoolUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,
                        String.valueOf(System.currentTimeMillis()+lockTimeOut));
                //再次用当前时间戳getset
                //返回给定的key的旧值,再进一步判断是否可以获取锁
                //key没有旧值时，即key不存在，返回nil
                if(getSetResult == null || (getSetResult != null && StringUtils.equals(lockValueStr,getSetResult))){
                    //真正获取到锁
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }else{
                    log.info("没有获取到分布式锁:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            }else{
                log.info("没有获取到分布式锁:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            }
        }
        log.info("关闭订单定时任务结束");
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void closeTaskV4(){
        RLock lock = redissonManager.getRedisson().getLock(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        boolean getLock = false;
        try {
            if(getLock = lock.tryLock(0, 50, TimeUnit.SECONDS)){
                log.info("Redisson获取分布式锁:{},TheadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
                int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
//                iOrderService.closeOrder(hour);
            }else{
                log.info("Redisson没有获取到分布式锁:{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("Redisson分布式锁获取异常", e);
        } finally {
            if(!getLock){
                return;
            }
            lock.unlock();
            log.info("Redisson分布式锁释放锁");
        }
    }

    private void closeOrder(String lockName){
        RedisShardedPoolUtil.expire(lockName, 5 );
        log.info("获取{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
//        iOrderService.closeOrder(hour);
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread());
        log.info("===============================");
    }

}
