package grails.plugin.cachememcached.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Cacheable {

    /**
     * (Spring Expression Language (SpEL) - ?) attribute for computing the key dynamically.
     * <p>Default is "", meaning all method parameters are considered as a key.
     */
    String key() default "";

    /**
     * (Spring Expression Language (SpEL) - ?) attribute used for conditioning the method caching.
     * <p>Default is "", meaning the method is always cached.
     */
    String condition() default "";
}
