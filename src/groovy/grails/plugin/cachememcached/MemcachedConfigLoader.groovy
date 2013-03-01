package grails.plugin.cachememcached

import grails.plugin.cache.ConfigLoader
import grails.plugin.cachememcached.exceptions.IllegalConfigFormatException
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.cache.Cache
import org.springframework.context.ApplicationContext

/**
 * @author Svyat Podmogayev
 */
class MemcachedConfigLoader extends ConfigLoader  {

    void reload(List<ConfigObject> configs, ApplicationContext ctx) {
        GrailsMemcachedManager cacheManager = ctx.grailsCacheManager

        for(ConfigObject co in configs) {
            Set keySet = co.keySet()
            for(String key in keySet) {
                Map value = (Map) co.get(key)
                String timeToLiveStr = value.timeToLive

                if (!timeToLiveStr) {
                    throw new IllegalConfigFormatException("Error! timeToLive-property is has`t set!")
                }

                int timeToLive = Integer.parseInt(timeToLiveStr)
                Cache memcachedCache = cacheManager.getCache(key)
                ((MemcachedCache)memcachedCache).setTimeToLive(timeToLive)
            }
        }
    }

    List<ConfigObject> loadOrderedConfigs(GrailsApplication application) {
        List<ConfigObject> configs = []

        def cacheConfig = application.config.memcached
        if(cacheConfig) {
            def cacheSettings = cacheConfig.settings

            if(cacheSettings) {
                def memcachedHost = cacheSettings.serverHost
                if(memcachedHost) { MemcachedCache.setMemcachedServerHost(memcachedHost) }

                def memcachedPort = cacheSettings.serverPort
                if(memcachedPort) { MemcachedCache.setMemcachedServerPort(memcachedPort) }
            }

            ConfigObject cachesList = cacheConfig.caches
            configs.addAll(cachesList)
        }

        configs
    }

    protected void sortConfigs(List<Closure> configs) {
        configs.sort { c1, c2 ->
            c1.order == c2.order ? c1._sourceClassName <=> c2._sourceClassName : c1.order <=> c2.order
        }
    }
}
