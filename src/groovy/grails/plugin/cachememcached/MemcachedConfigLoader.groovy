package grails.plugin.cachememcached

import grails.plugin.cache.ConfigLoader
import org.springframework.context.ApplicationContext
/**
 * @author: prostohz
 */
class MemcachedConfigLoader extends ConfigLoader  {

    void reload(List<ConfigObject> configs, ApplicationContext ctx) {
        GrailsMemcachedManager cacheManager = (GrailsMemcachedManager) ctx.grailsCacheManager

        MemcachedCache memcachedCache = cacheManager.getCache();
        memcachedCache.setTimeToLive(3600)
    }
}

