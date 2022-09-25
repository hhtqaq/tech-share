package com.shopee.study.ratelimiter.algorithm;

import com.google.common.base.Stopwatch;
import com.shopee.study.ratelimiter.exception.BizException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 * @author haitao.huang
 */
public class RateLimitAlg {
    // 200ms
    private static final long          TRY_LOCK_TIMEOUT = 200L;
    private final        Stopwatch     stopwatch;
    private final        AtomicInteger currentCount     = new AtomicInteger(0);
    private final        int           limit;

    private final int unit;
    private final        Lock          lock             = new ReentrantLock();

    public RateLimitAlg(int limit,int unit) {
        this(limit,unit, Stopwatch.createStarted());
    }

    protected RateLimitAlg(int limit, int unit,Stopwatch stopwatch) {
        this.limit = limit;
        this.unit = unit;
        this.stopwatch = stopwatch;
    }

    /**
     * 固定时间窗口大小限流
     */
    public boolean tryAcquire()  throws BizException{
        int updatedCount = currentCount.incrementAndGet();
        if (updatedCount <= limit) {
            return true;
        }
        try {
            //加锁
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                try {
                    //达到限流条件后判断是否大于时间窗口 大于则重置窗口
                    if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > TimeUnit.MILLISECONDS.toMillis(unit)) {
                        currentCount.set(0);
                        stopwatch.reset();
                        stopwatch.start();
                    }
                    updatedCount = currentCount.incrementAndGet();
                    return updatedCount <= limit;

                } finally {
                    lock.unlock();
                }
            } else {
                throw new BizException("tryAcquire() wait lock too long:");
            }
        } catch (InterruptedException e) {
            throw new BizException("tryAcquire() is interrupted by lock-time-out",e);
        }
    }
}