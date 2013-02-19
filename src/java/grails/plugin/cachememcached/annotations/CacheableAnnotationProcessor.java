package grails.plugin.cachememcached.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author prostohz
 * @since 2/19/13 3:04 PM
 */

@SupportedAnnotationTypes(value = {CacheableAnnotationProcessor.ANNOTATION_TYPE})
public class CacheableAnnotationProcessor extends AbstractProcessor {

    public static final String ANNOTATION_TYPE = "grails.plugin.cachememcached.annotations.Cacheable";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations == null || annotations.isEmpty()) {
            return false;
        }

        return false;
    }
}
