import grails.plugin.cachememcached.CacheBeanPostProcessor
import grails.plugin.cachememcached.GrailsMemcachedManager
import grails.plugin.cachememcached.MemcachedConfigLoader

import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * @author Svyat Podmogayev
 */
class CacheMemcachedGrailsPlugin {

    def version = "0.1-SNAPSHOT"
    def grailsVersion = "2.2 > *"

    def loadAfter = ['cache']

    def title       = "Cache Memcached Plugin"
    def author      = "Svyat Podmogayev, Dmitry Kurinskiy"
    def authorEmail = "s.podmogayev@gmail.com, name.alari@gmail.com"
    def description = "The plugin allows to use the memcached util for caching of data"

    def license = "APACHE"
    def issueManagement = [ system: "JIRA", url: "https://github.com/alari/cache-memcached/issues" ]
    def scm = [ url: "https://github.com/alari/cache-memcached" ]
    def documentation = "https://github.com/alari/cache-memcached"

    def developers = [
            [ name: "Svyat Podmogayev", email: "s.podmogayev@gmail.com" ],
            [ name: "Dmitry Kurinskiy", email: "name.alari@gmail.com" ]
    ]

    def doWithSpring = {
        if (!isEnabled(application)) {
            return
        }

        cacheBeanPostProcessor(CacheBeanPostProcessor)
        grailsCacheManager(GrailsMemcachedManager)
        grailsCacheConfigLoader(MemcachedConfigLoader)
    }

    private boolean isEnabled(GrailsApplication application) {
        def memcachedConfig = application.config.memcached
        if (memcachedConfig) {
            def enabled = memcachedConfig.enabled
            println "enabled: ${enabled}"
            if (enabled != [:])
                return enabled
        }
        return true
    }
}
