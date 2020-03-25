package com.itensis.ecat.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationSearcher {

    public List<Method> getMethodsAnnotatedWith(Class<?> type, Class<? extends Annotation> annotation) {
        Method[] methods = type.getMethods();
        List<Method> annotatedMethods = new ArrayList<>(methods.length);
        for (Method method : methods) {
            if( method.isAnnotationPresent(annotation)){
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    public List<String> getRequestMappingValue(List<Method> methods){
        List<String> values = new ArrayList<>();
        for(Method method : methods){
            values.addAll(Arrays.asList(method.getAnnotation(RequestMapping.class).value()));
        }
        return values;
    }

    public List<Class> getAllRestController(){
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        List<Class> classes = new ArrayList<>();
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents("com.itensis.ecat.controller")){
            try {
                classes.add(Class.forName(beanDefinition.getBeanClassName()));
            }catch (ClassNotFoundException ignored){}
        }
        return classes;
    }

}
