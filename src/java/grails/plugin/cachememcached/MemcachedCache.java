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

    private static final String  MEMCACHED_SERVER_HOST = "localhost";
    private static final int MEMCACHED_SERVER_PORT = 11211;

    private static final int DEFAULT_EXPIRATION_TIME = 600;

    private static final Object NULL = "NULL";

    private String name;

    private MemcachedClient memcachedClient;


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

        ValueWrapper valueWrapper = new ValueWrapper() {
            @Override
            public Object get() {
                if(value != null)
                    return value;
                else
                    return NULL;
            }
        };
        return valueWrapper;
    }

    @Override
    public void put(Object o, Object o2) {
        String key = o.toString();

        System.out.println("key: " + key);
        System.out.println("value: " + o2);

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
