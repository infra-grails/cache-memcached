package grails.plugin.cachememcached;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.springframework.cache.Cache;

/**
 * @author : prostohz
 * @since : 2/21/13 1:19 PM
 */
public class MemcachedCache implements Cache {

    public static final String DEFAULT_CACHE_NAME = "memcached";

    private static String  memcachedServerHost = "localhost";
    public static void setMemcachedServerHost(String host) { memcachedServerHost = host; }

    private static int memcachedServerPort = 11211;
    public static void setMemcachedServerPort(int port) { memcachedServerPort = port; }

    private String name;
    private MemcachedClient memcachedClient;

    private MemcachedStatistics statistics;

    private int expirationTime = 0;

    public MemcachedCache(String name) {
        this.name = name;
        try {
            memcachedClient = new MemcachedClient(AddrUtil.getAddresses(
                    memcachedServerHost + ":" + memcachedServerPort));
            statistics = new MemcachedStatistics();
        } catch (IOException e) {
            System.out.println("Exception has occurred with MemcachedClient initialization");
        }
    }

    public void setTimeToLive(int timeToLive){
        expirationTime = timeToLive;
    }

    public String getName() {
        return name;
    }

    public Object getNativeCache() {
        return memcachedClient;
    }

    public ValueWrapper get(Object o) {
        statistics.incCmdGet();

        String key = o.toString();
        final Object value = memcachedClient.get(key);
        if(value != null) {
            ValueWrapper valueWrapper = new ValueWrapper() {
                public Object get() {
                    return value;
                }
            };
            return valueWrapper;
        } else {
            return null;
        }
    }

    public void put(Object o, Object o2) {
        statistics.incCmdSet();

        String key = o.toString();
        memcachedClient.set(key, expirationTime, o2);
    }

    public void evict(Object o) {
        statistics.incCmdEvct();

        String key = o.toString();
        memcachedClient.delete(key);
    }

    public void clear() {
        memcachedClient.flush();
    }

    public MemcachedStatistics getStatistics() {
        return statistics;
    }
}
