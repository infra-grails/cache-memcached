grails.project.work.dir = 'target'

grails.project.repos.default = "quonb-snapshot"

grails.project.dependency.distribution = {
    String serverRoot = "http://mvn.quonb.org"
    remoteRepository(id: 'quonb-snapshot', url: serverRoot + '/plugins-snapshot-local/')
    remoteRepository(id: 'quonb-release', url: serverRoot + '/plugins-release-local/')
}

grails.project.dependency.resolution = {
    inherits 'global'
    log 'warn'
    legacyResolve true

    repositories {
        grailsCentral()
        mavenRepo "http://files.couchbase.com/maven2"
        mavenLocal()
        mavenCentral()
        mavenRepo "http://mvn.quonb.org/repo"
        grailsRepo "http://mvn.quonb.org/repo", "quonb"
    }

    dependencies {
        compile 'spy:spymemcached:2.8.11'

        test("org.spockframework:spock-grails-support:0.7-groovy-2.0") {
            export = false
        }
    }

    plugins {
        compile(":release:2.2.0", ":rest-client-builder:1.0.3") {
            export = false
        }

        compile ':cache:1.0.1'

        test(":spock:0.7") {
            exclude "spock-grails-support"
            export = false
        }
    }
}
