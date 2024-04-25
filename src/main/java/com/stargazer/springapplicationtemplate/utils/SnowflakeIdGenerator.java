package com.stargazer.springapplicationtemplate.utils;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 雪花ID生成器
 * @author Stargazer
 */
public class SnowflakeIdGenerator {
    private static final long EPOCH_START = Instant.parse("2024-01-01T00:00:00Z").toEpochMilli(); // 起始时间戳，可根据实际情况调整

    private final long dataCenterId; // 数据中心ID
    private final long workerId; // 工作节点ID
    private final AtomicInteger sequence; // 序列号（原子整数）

    /**
     * 雪花ID生成器
     * 41位的时间戳（毫秒级，可使用约69年）
     * @param dataCenterId 10位的数据中心ID（最大支持1024个数据中心）
     * @param workerId 12位的工作节点ID（最大支持4096个工作节点）
     * @param sequence 12位的序列号（每个节点每毫秒最多生成4096个ID）
     */
    public SnowflakeIdGenerator(int dataCenterId, int workerId, int sequence) {
        if (dataCenterId < 0 || dataCenterId > (1 << 10) - 1) {
            throw new IllegalArgumentException("Invalid data center ID");
        }
        if (workerId < 0 || workerId > (1 << 12) - 1) {
            throw new IllegalArgumentException("Invalid worker ID");
        }
        if(sequence < 0 || sequence > (1 << 12) - 1)
        {
            throw new IllegalArgumentException("Invalid sequence");
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
        this.sequence = new AtomicInteger(sequence);
    }

    /**
     * 生成下一个雪花ID
     *
     * @return 雪花ID（long类型）
     */
    public synchronized long nextId() {
        long currentTimeMillis = Instant.now().toEpochMilli();
        if (currentTimeMillis < EPOCH_START) {
            throw new IllegalStateException("Clock is moving backwards, refusing to generate id for " + currentTimeMillis);
        }

        long timestamp = currentTimeMillis - EPOCH_START;
        long sequenceBits = sequence.incrementAndGet() & ((1 << 12) - 1); // 保证序列号在范围内

        // 组合各个部分
        return ((timestamp << (12 + 10)) // 时间戳左移
                | (dataCenterId << 12) // 数据中心ID左移
                | workerId // 工作节点ID
                | sequenceBits); // 序列号
    }
}