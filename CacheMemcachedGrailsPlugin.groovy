import grails.plugin.cachememcached.CacheBeanPostProcessor
import grails.plugin.cachememcached.GrailsMemcachedManager
import grails.plugin.cachememcached.MemcachedConfigLoader

import org.codehaus.groovy.grails.commons.GrailsApplication

class CacheMemcachedGrailsPlugin {
    def version = "0.1-SNAPSHOT"
    def grailsVersion = "2.2 > *"

    def title       = "Cache Memcached Plugin"
    def author      = "Svyat Podmogayev"
    def authorEmail = "s.podmogayev@gmail.com"
    def description = "The plugin allows to use the memcached util for caching of data"

    def documentation = "https://github.com/alari/cache-memcached"

    def doWithSpring = {
        if (!isEnabled(application)) {
            return
        }

        cacheBeanPostProcessor(CacheBeanPostProcessor)
        grailsCacheManager(GrailsMemcachedManager)
        grailsCacheConfigLoader(MemcachedConfigLoader)
    }

    private boolean isEnabled(GrailsApplication application) {
        true
    }
}
