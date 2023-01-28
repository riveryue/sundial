package sundial.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author yao
 */
public class ZKLockUtil {

    private static String address = "192.168.1.104:2181";

    public static CuratorFramework client;

    static {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        client.start();
    }

    /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    private ZKLockUtil() {
    }

    private static class SingletonHolder {
        private static InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");
    }

    public static InterProcessMutex getMutex() {
        return SingletonHolder.mutex;
    }

    public static boolean acquire(long time, TimeUnit unit) {
        try {
            return getMutex().acquire(time, unit);
        } catch (Exception e) {

            return false;
        }
    }

    public static void release() {
        try {
            getMutex().release();
        } catch (Exception e) {

        }
    }
}



