import grails.plugin.cache.CacheBeanPostProcessor
import grails.plugin.cache.GrailsAnnotationCacheOperationSource
import grails.plugin.cachememcached.MemcachedConfigLoader
import grails.plugin.cachememcached.GrailsMemcachedManager
import org.codehaus.groovy.grails.commons.GrailsApplication

class CacheMemcachedGrailsPlugin {
    def version = "0.1-SNAPSHOT"
    def grailsVersion = "2.2 > *"
    def loadAfter = ['cache']
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title       = "Cache Memcached Plugin"
    def author      = "Svyat Podmogayev"
    def authorEmail = "s.podmogayev@gmail.com"
    def description = "The plugin allows to use the memcached util for caching of data"
    def documentation = "http://grails.org/plugin/cache-memcached"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        cacheBeanPostProcessor(CacheBeanPostProcessor)
        grailsCacheManager(GrailsMemcachedManager)
        grailsCacheConfigLoader(MemcachedConfigLoader)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
