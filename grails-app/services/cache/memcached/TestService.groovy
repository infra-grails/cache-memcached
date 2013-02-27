package cache.memcached

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.CachePut
import grails.plugin.cache.Cacheable

class TestService {

    @Cacheable('message')
    Message getMessage(String title) {
        println 'Fetching message'
        Message.findOrCreateByTitle(title)
    }

    @CachePut(value='message', key='#message.title')
    void save(Message message) {
        println "Saving message $message"
        message.save()
    }

    @CacheEvict(value='message', key='#message.title')
    void delete(Message message) {
        println "Deleting message $message"
        message.delete()
    }
}