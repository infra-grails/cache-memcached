package cache.memcached

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable

class TestService {

    @Cacheable(value = "memcached", key="#title")
    def createDomain(String title) {
        new TestDomain(title: title).save()
    }

    @CacheEvict(value = "memcached", key="#user.id.toString()")
    def deleteDomain(Long id) {
        TestDomain.findById(id).delete()
    }
}
