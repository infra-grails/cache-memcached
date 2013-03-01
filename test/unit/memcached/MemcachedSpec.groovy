package memcached

import grails.plugin.cachememcached.GrailsMemcachedManager
import grails.plugin.cachememcached.MemcachedCache

import org.springframework.cache.Cache
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * @author :
 * @since : 2/22/13 2:33 PM
 */
@Stepwise
class MemcachedSpec extends Specification {

    @Shared
    int memcachedTTL = 10

    @Shared
    def returnValue

    @Shared
    Cache memcachedCache

    @Shared
    Cache secondMemcachedCache

    @Shared
    GrailsMemcachedManager grailsMemcachedManager

    def setupSpec() {
        grailsMemcachedManager  = new GrailsMemcachedManager()

        memcachedCache = grailsMemcachedManager.getCache()
        ((MemcachedCache)memcachedCache).setTimeToLive(memcachedTTL)

        memcachedCache.clear()
    }

    void "Can create more than one MemcachedCache instance"() {
        given:
        int secondMemcachedCacheTTL = 10

        when: "Trying to create second MemcachedCache instance"
        secondMemcachedCache = grailsMemcachedManager.getCache("secondMemcachedCache")
        ((MemcachedCache)secondMemcachedCache).setTimeToLive(secondMemcachedCacheTTL)

        then:
        memcachedCache != secondMemcachedCache
    }

    void "Can get cache name"() {
        when: "Trying to get name"
        String name = memcachedCache.getName()

        then:
        name == MemcachedCache.DEFAULT_CACHE_NAME
    }

    void "Can put data in memcached"() {
        given:
        String testKey   = "testKey1"
        String testValue = "testValue1"

        when:
        returnValue = memcachedCache.get(testKey)

        then:
        noExceptionThrown()
        returnValue == null

        when: "Tying to put test value associated with test key"
        memcachedCache.put(testKey, testValue)

        then:
        noExceptionThrown()

        when: "Trying to get putted value"
        returnValue = memcachedCache.get(testKey)

        then:
        noExceptionThrown()
        returnValue != null
        returnValue.get() == testValue
    }

    void "Can evict value"() {
        given:
        String testKey   = "testKey2"
        String testValue = "testValue2"

        when: "Tying to put test value associated with test key"
        memcachedCache.put(testKey, testValue)
        then:
        noExceptionThrown()

        when:
        memcachedCache.evict(testKey)
        returnValue = memcachedCache.get(testKey)

        then:
        returnValue == null
    }

    void "Can flush all"() {
        given:
        String testKey   = "testKey3"
        String testValue = "testValue3"

        when: "Tying to put test value associated with test key"
        memcachedCache.put(testKey, testValue)
        then:
        noExceptionThrown()


        when: "Trying to flush all"
        memcachedCache.clear()
        returnValue = memcachedCache.get(testKey)

        then: "There are`t an earlier existing values"
        returnValue == null
    }

    void "Does delete after ttl will end"() {
        given:
        String testKey   = "testKey4"
        String testValue = "testValue4"

        when:
        secondMemcachedCache.put(testKey, testValue)
        sleep(5 * 1000)
        returnValue = secondMemcachedCache.get(testKey)

        then:
        returnValue.get() == testValue

        when:
        sleep(6 * 1000)
        returnValue = secondMemcachedCache.get(testKey)

        then:
        returnValue == null
    }
}
