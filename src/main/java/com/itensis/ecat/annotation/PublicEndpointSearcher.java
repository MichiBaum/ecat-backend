package com.itensis.ecat.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class PublicEndpointSearcher {

    private List<String> restControllerPackages;

    public PublicEndpointSearcher(String... restControllerPackages){
        this.restControllerPackages = Arrays.asList(restControllerPackages);
    }

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

    public List<PublicEndpointDetails> getRequestMappingValue(Method method){
        List<PublicEndpointDetails> values = new ArrayList<>();
        values.add(
            new PublicEndpointDetails(
                Arrays.asList(method.getAnnotation(RequestMapping.class).value()),
                method.getAnnotation(PublicEndpoint.class).character(),
                method.getAnnotation(PublicEndpoint.class).numerus()
            )
        );
        return values;
    }

    public List<Class> getAllRestController(){
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        List<BeanDefinition> beanDefinitions = restControllerPackages.stream().map(scanner::findCandidateComponents).flatMap(Collection::parallelStream).collect(Collectors.toList());
        return beanDefinitions.stream().map(beanDefinition -> {
            try {
                return Class.forName(beanDefinition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

}
