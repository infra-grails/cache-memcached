import net.spy.memcached.AddrUtil
import net.spy.memcached.MemcachedClient
import org.springframework.beans.factory.InitializingBean

/**
 * @author: prostohz
 */
class MemcachedService implements InitializingBean {

    private static final String  MEMCACHED_SERVER_HOST = 'localhost'
    private static final Integer MEMCACHED_SERVER_PORT = 11211

    private static final Integer DEFAULT_EXPIRATION_TIME = 600

    private static final Object NULL = "NULL"

    MemcachedClient memcachedClient

    def void afterPropertiesSet() {
        memcachedClient = new MemcachedClient(AddrUtil.getAddresses(
                "${MEMCACHED_SERVER_HOST}:${MEMCACHED_SERVER_PORT}")
        )
    }

    def get(String key) {
        return memcachedClient.get(key)
    }

    def set(String key, Object value) {
        memcachedClient.set(key, DEFAULT_EXPIRATION_TIME, value)
    }

    def delete(String key) {
        memcachedClient.delete(key)
    }

    def clear() {
        memcachedClient.flush()
    }

    def update(key, function) {
        def value = function()
        if (value == null) value = NULL
        set(key, value)
        return value
    }

    def get(key, function) {
        def value = get(key)
        if (value == null) {
            value = update(key, function)
        }
        return (value == NULL) ? null : value;
    }
}