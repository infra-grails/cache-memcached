package grails.plugin.cachememcached;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.springframework.cache.Cache;

import java.io.IOException;

/**
 * @author : prostohz
 * @since : 2/21/13 1:19 PM
 */
public class MemcachedCache implements Cache {

    public static final String CACHE_NAME = "memcached";

    private static final String  MEMCACHED_SERVER_HOST = "localhost";
    private static final int MEMCACHED_SERVER_PORT = 11211;

    public static int DEFAULT_EXPIRATION_TIME = 3600;

    private static final Object  NULL = "NULL";

    private MemcachedClient memcachedClient;
    private String name;

    public MemcachedCache(String name) {
        this.name = name;
        try {
            memcachedClient = new MemcachedClient(AddrUtil.getAddresses(
                    MEMCACHED_SERVER_HOST + ":" + MEMCACHED_SERVER_PORT));
        } catch (IOException e) {
            System.out.println("Exception has occurred with MemcachedClient initialization");
        }
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
        memcachedClient.set(key,DEFAULT_EXPIRATION_TIME, o2);
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
