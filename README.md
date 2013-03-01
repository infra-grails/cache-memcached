Auhor: Svyat Podmogayev (s.podmogayev@gmail.com) with the support of Dmitry Kurinskiy.

The plugin depends on *cache:1.0.1* plugin and implements an adapter to `memcached` cache for Grails caching infrastructure.

Usage
==============

Take a look on a base `cache` plugin to find out its usage. `cache-memcached` plugin only adapts memcached to back the cache.

Configuration
==============

```groovy
memcached {
    // If you need to switch off the plugin -- optional
    enabled = false

    // Memcached server properties -- optional
    settings {
        serverHost = "localhost"
        serverPort = 11211
    }

    // The only property supported is timeToLive (seconds)
    // Make individual TTL settings for different caches.
    caches {
        userProfiles {
            timeToLive = 7200
        }
        taxonomy {
            timeToLive = 0
        }
        stream {
            timeToLive = 0
        }
    }
}
```
