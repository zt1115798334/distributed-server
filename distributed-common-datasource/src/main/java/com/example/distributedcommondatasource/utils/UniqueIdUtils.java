package com.example.distributedcommondatasource.utils;

import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2020/12/25 15:04
 * description:
 */
public class UniqueIdUtils {

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

    public static long SnowflakeShardingKeyGenerator() {
        return (Long) new SnowflakeShardingKeyGenerator().generateKey();
    }
}
