package grails.plugin.cachememcached;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.springframework.cache.Cache;

import java.io.IOException;

/**
 * @author : prostohz
 * @since : 2/21/13 1:19 PM
 */
public class MemcachedCache implements Cache {

    public static final String DEFAULT_CACHE_NAME = "memcached";

    private static final String  memcachedServerHost = "localhost";
    private static final int     memcachedServerPort = 11211;

    private String name;
    private MemcachedClient memcachedClient;

    private int expirationTime;
    {
        expirationTime = 0;
    }

    public MemcachedCache(String name) {
        this.name = name;
        try {
            memcachedClient = new MemcachedClient(AddrUtil.getAddresses(
                    memcachedServerHost + ":" + memcachedServerPort));
        } catch (IOException e) {
            System.out.println("Exception has occurred with MemcachedClient initialization");
        }
    }

    public void setTimeToLive(int timeToLive){
        expirationTime = timeToLive;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return memcachedClient;
    }

    @Override
    public ValueWrapper get(Object o) {
        String key = (String) o;

        final Object value = memcachedClient.get(key);
        if(value != null) {
            ValueWrapper valueWrapper = new ValueWrapper() {
                @Override
                public Object get() {
                    return value;
                }
            };
            return valueWrapper;
        } else {
            return null;
        }
    }

    @Override
    public void put(Object o, Object o2) {
        String key = (String) o;
        memcachedClient.set(key, expirationTime, o2);
    }

    @Override
    public void evict(Object o) {
        String key =  (String) o;
        memcachedClient.delete(key);
    }

    @Override
    public void clear() {
        memcachedClient.flush();
    }
}
