package com.shopee.study.ratelimiter.alg;

import com.google.common.base.Stopwatch;
import com.shopee.study.ratelimiter.exception.BizException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author haitao.huang
 */
public class RateLimitAlg {
    // 200ms
    private static final long          TRY_LOCK_TIMEOUT = 200L;
    private final        Stopwatch     stopwatch;
    private final        AtomicInteger currentCount     = new AtomicInteger(0);
    private final        int           limit;
    private final        Lock          lock             = new ReentrantLock();

    public RateLimitAlg(int limit) {
        this(limit, Stopwatch.createStarted());
    }

    protected RateLimitAlg(int limit, Stopwatch stopwatch) {
        this.limit = limit;
        this.stopwatch = stopwatch;
    }

    public boolean tryAcquire()  throws BizException{
        int updatedCount = currentCount.incrementAndGet();
        if (updatedCount <= limit) {
            return true;
        }
        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                try {
                    if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > TimeUnit.SECONDS.toMinutes(limit)) {
                        currentCount.set(0);
                        stopwatch.reset();
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
            throw new BizException("tryAcquire() is interrupted");
        }
    }
}