package com.zwj;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis实现分布式锁
 *
 * @author zwj
 * @date 2021年09月08日
 */
public class DistributedLock {

    private static RedisTemplate redisTemplate;
    private static Redisson redisson;

    @Autowired
    public DistributedLock(RedisTemplate redisTemplate) {
        DistributedLock.redisTemplate = redisTemplate;
    }

    @Autowired
    public DistributedLock(Redisson redisson) {
        DistributedLock.redisson = redisson;
    }

    /**
     * 模拟高并发抢单场景--采用redis实现
     * 注：当redis集群,当线程1刚加锁，master节点挂了，lock还没来的及同步到从节点，
     * 从节点升级为主节点没有lock，此种情况高并发就会出问题。
     *
     * @return
     */
    public Object addLock1() {
        String lockKey = "lockKey";
        // 设置uuid的意义：防止线程1释放了线程2的锁，导致锁失效。
        String uuid = UUID.randomUUID().toString();

        try {
            // 1、设置过期时间的意义：当服务宕机，能保证锁被始放

            // 相较于b，不能保证代码的原子性，若执行到redisTemplate.opsForValue().setIfAbsent，
            // 发生错误，那么设置过期时间的代码不会执行，锁不会释放，发生死锁
            //Boolean a = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid);
            //redisTemplate.expire(lockKey,5,TimeUnit.SECONDS);

            // redisTemplate.opsForValue().setIfAbsent当前存在这个key不缓存数据，并返回false，此方法保证了设置过期时间的原子性
            Boolean b = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, 5, TimeUnit.SECONDS);

            if (!b) {
                return "当前抢购人数太多，请稍等！";
            }

            // 执行具体的业务逻辑

        } finally {
            // 2、引入try-finally 保证程序异常时释放锁
            // 释放锁
            if (redisTemplate.opsForValue().get(lockKey).equals(uuid)) {
                // 1、只有当锁的value值uuid和生成的uuid相等，才能释放自己的锁
                // 2、存在问题：判断uuid和删除锁操作不是原子操作，有可能线程1判断uuid通过,但是锁过期刚好释放，接着执行了释放线程2的锁。执行lua脚本
                //redisTemplate.delete(lockKey);

                // 定义lua 脚本
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                // 使用redis执行lua执行
                DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
                redisScript.setScriptText(script);
                // 设置一下返回值类型 为Long
                // 因为删除判断的时候，返回的0,给其封装为数据类型。如果不封装那么默认返回String 类型，
                // 那么返回字符串与0 会有发生错误。
                redisScript.setResultType(Long.class);
                // 第一个要是script 脚本 ，第二个需要判断的key，第三个就是key所对应的值。
                redisTemplate.execute(redisScript, Arrays.asList(lockKey), uuid);
            }
        }
        return "抢单成功！";
    }

    /**
     * 模拟高并发抢单场景--采用redisson实现
     *
     * @return
     */
    public Object addLock2() {
        String lockKey = "lockKey";
        RLock lock = redisson.getLock(lockKey);
        try {
            boolean b = lock.tryLock(1, 3, TimeUnit.SECONDS);
            if (b) {
                return "当前抢购人数太多，请稍等！";
            }

            // 执行具体的业务逻辑

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 2、引入try-finally 保证程序异常时释放锁
            // 释放锁
            lock.unlock();
        }
        return "抢单成功！";
    }
}
