package memcached

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

@Ignore
@Stepwise
class AnnotationsApplicationSpec extends Specification {

    def testService
    @Shared
    Cache memcahedCache

    @Shared
    MemcachedStatistics statistics

    def setupSpec() {
        GrailsMemcachedManager grailsMemcachedManager  = new GrailsMemcachedManager()
        memcahedCache = grailsMemcachedManager.getCache()

        statistics = ((MemcachedCache)memcahedCache).getStatistics()
    }

    void "Does cacheable-annotation applies with sevice method"() {
        given:
        String testTitle = "testTitle"
        int cmdSet = statistics.getCmdSet()

        when:
        testService.createDomain()

        then:
        noExceptionThrown()
        cmdSet + 1 == statistics.getCmdGet()

    }

    void "Does cacheEvict-annotation applies with serivce method"() {
        given:
        Long testDomainId = 1
        int cmdEvct = statistics.getCmdEvct()

        when:
        testService.deleteDomain(testDomainId)

        then:
        noExceptionThrown()
        cmdEvct + 1 == statistics.getCmdEvct()
    }
}
