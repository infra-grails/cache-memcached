package cache.memcached

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable

class TestController {

    @Cacheable(value = "memcached", key="#title")
    def createDomain(String title) {

    }

    @CacheEvict(value = "memcached", key="#user.id.toString()")
    def deleteDomain(Long id) {

    }
}
