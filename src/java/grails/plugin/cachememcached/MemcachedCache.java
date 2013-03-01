package grails.plugin.cachememcached;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.springframework.cache.Cache;

/**
 * @author Svyat Podmogayev
 * @since 2/21/13 1:19 PM
 */
public class MemcachedCache implements Cache {

    public static boolean DEBUG = false;
    public static final String DEFAULT_CACHE_NAME = "memcached";

    private static String  memcachedServerHost = "localhost";

    public static void setMemcachedServerHost(String host) {
        memcachedServerHost = host;
    }

    private static int memcachedServerPort = 11211;

    public static void setMemcachedServerPort(int port) {
        memcachedServerPort = port;
    }


    private MemcachedClient memcachedClient;
    private String name;
    private int expirationTime = 0;
    private MemcachedStatistics statistics;



    public MemcachedCache(String name) {
        this.name = name;
        try {
            memcachedClient = new MemcachedClient(AddrUtil.getAddresses(
                    memcachedServerHost + ":" + memcachedServerPort));
        } catch (IOException e) {
            System.out.println("Error! Exception has occurred with MemcachedClient initialization");
        }
        statistics = new MemcachedStatistics();
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

        if(DEBUG) System.out.println("get:key: " + key);

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

        if(DEBUG) System.out.println("get:key: " + key);

        memcachedClient.set(key, expirationTime, o2);
    }

    public void evict(Object o) {
        statistics.incCmdEvct();

        String key = o.toString();

        if(DEBUG) System.out.println("evict:key: " + key);

        memcachedClient.delete(key);
    }

    public void clear() {
        memcachedClient.flush();
    }

    public MemcachedStatistics getStatistics() {
        return statistics;
    }
}
