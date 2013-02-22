package grails.plugin.cachememcached;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author : prostohz
 * @since : 2/21/13 1:21 PM
 */
public class GrailsMemcachedManager implements CacheManager {

    protected final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    public GrailsMemcachedManager() { }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(cacheMap.keySet());
    }

    public Cache getCache() {
        return getCache(MemcachedCache.DEFAULT_CACHE_NAME);
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache == null) {
            synchronized (cacheMap) {
                cache = cacheMap.get(name);
                if (cache == null) {
                    cache = createMemcachedCache(name);
                    cacheMap.put(name, cache);
                }
            }
        }
        return cache;
    }

    public boolean cacheExists(String name) {
        return getCacheNames().contains(name);
    }

    public boolean destroyCache(String name) {
        synchronized (cacheMap) {
            return cacheMap.remove(name) != null;
        }
    }

    protected MemcachedCache createMemcachedCache(String name) {
        return new MemcachedCache(name);
    }
}
