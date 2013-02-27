package memcached

import cache.memcached.TestService
import grails.plugin.cachememcached.GrailsMemcachedManager
import grails.plugin.cachememcached.MemcachedCache
import grails.plugin.cachememcached.MemcachedStatistics
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.Cache

import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * User: prostohz
 * Date: 2/25/13 5:19 PM
 */

@Stepwise
class AnnotationsApplicationSpec extends Specification {

    @Autowired
    TestService testService

    @Shared
    Cache memcachedCache

    @Shared
    MemcachedStatistics statistics

    def setupSpec() {
        GrailsMemcachedManager grailsMemcachedManager  = new GrailsMemcachedManager()
        memcachedCache = grailsMemcachedManager.getCache()

        statistics = ((MemcachedCache)memcachedCache).getStatistics()
    }

    void "Does cacheable-annotation applies with sevice method"() {
        given:
        String testTitle = "testTitle"
        int cmdSet = statistics.getCmdSet()

        when:
        testService.getMessage()

        then:
        noExceptionThrown()
        cmdSet + 1 == statistics.getCmdGet()

    }
}
