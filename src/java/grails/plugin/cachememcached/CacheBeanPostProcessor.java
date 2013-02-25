package grails.plugin.cachememcached;

import grails.plugin.cache.GrailsAnnotationCacheOperationSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;

/**
 * @author : prostohz
 * @since : 2/25/13 6:11 PM
 */
public class CacheBeanPostProcessor implements BeanDefinitionRegistryPostProcessor {

    protected Logger log = LoggerFactory.getLogger(getClass());

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        log.info("postProcessBeanDefinitionRegistry start");

        AbstractBeanDefinition beanDef = findBeanDefinition(registry);
        if (beanDef == null) {
            log.error("Unable to find the AnnotationCacheOperationSource bean definition");
            return;
        }

        // change the class to the plugin's subclass
        // change the class to the plugin's subclass
        beanDef.setBeanClass(GrailsAnnotationCacheOperationSource.class);

        // wire in the dependency for the grailsApplication
        MutablePropertyValues props = beanDef.getPropertyValues();
        if (props == null) {
            props = new MutablePropertyValues();
            beanDef.setPropertyValues(props);
        }
        props.addPropertyValue("grailsApplication", new RuntimeBeanReference("grailsApplication", true));

        log.debug("updated {}", beanDef);
    }

    protected AbstractBeanDefinition findBeanDefinition(BeanDefinitionRegistry registry) {

        AbstractBeanDefinition beanDef = null;
        String beanName = null;

        if (registry.containsBeanDefinition(GrailsAnnotationCacheOperationSource.BEAN_NAME)) {
            beanDef = (AbstractBeanDefinition)registry.getBeanDefinition(
                    GrailsAnnotationCacheOperationSource.BEAN_NAME);
            beanName = GrailsAnnotationCacheOperationSource.BEAN_NAME;
        }
        else {
            String className = AnnotationCacheOperationSource.class.getName();
            for (String name : registry.getBeanDefinitionNames()) {
                if (className.equals(registry.getBeanDefinition(name).getBeanClassName())) {
                    beanDef = (AbstractBeanDefinition)registry.getBeanDefinition(name);
                    beanName = name;
                    break;
                }
            }
        }

        if (beanDef != null) {
            // make it easier to work with
            if (!"cacheOperationSource".equals(beanName)) {
                registry.registerAlias(beanName, "cacheOperationSource");
            }
        }

        return beanDef;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        log.info("postProcessBeanFactory");
    }
}
