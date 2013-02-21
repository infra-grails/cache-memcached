import grails.plugin.cachememcached.GrailsMemcachedManager

class CacheMemcachedGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.2 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title       = "Cache Memcached Plugin" // Headline display name of the plugin
    def author      = "Svyat Podmogayev"
    def authorEmail = "s.podmogayev@gmail.com"
    def description = ""

    def documentation = "http://grails.org/plugin/cache-memcached"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        def memcachedServer = application.config.memcached.sever

        String memcachedHost = memcachedServer.host
        String memcachedPort = memcachedServer.port
        String timeToLive = memcachedServer.ttl

        grailsCacheManager(GrailsMemcachedManager)
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
