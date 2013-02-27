log4j = {
   error 'org.codehaus.groovy.grails',
         'org.springframework',
         'org.hibernate',
         'net.sf.ehcache.hibernate'
}

memcached {
    caches {
        message {
            timeToLive = 0
        }
    }

}
