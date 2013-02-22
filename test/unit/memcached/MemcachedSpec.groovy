package memcached

import grails.plugin.cachememcached.MemcachedCache
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared;
import spock.lang.Specification;
import spock.lang.Stepwise;

/**
 * @author : prostohz
 * @since : 2/22/13 2:33 PM
 */
import spock.lang.Stepwise;
public class MemcachedSpec extends Specification {

    @Shared
    def returnValue

    @Shared
    MemcachedCache memcachedCache

    def setupSpec() {
        memcachedCache = new MemcachedCache(MemcachedCache.CACHE_NAME)
    }

    void "Can put data in memcached"() {
        given:
        String testKey = "testKey1"
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
        String testKey = "testKey2"
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
        String testKey = "testKey3"
        String testValue = "testValue3"

        when: "Tying to put test value associated with test key"
        memcachedCache.put(testKey, testValue)
        then:
        noExceptionThrown()


        when: "Trying to flush all"
        memcachedCache.clear()
        returnValue = memcachedCache.get(testKey)

        then:
        returnValue == null
    }
}
