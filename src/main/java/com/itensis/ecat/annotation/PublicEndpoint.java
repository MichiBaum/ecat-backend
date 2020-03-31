package com.itensis.ecat.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PublicEndpoint {

    Numerus numerus() default Numerus.NONE;

    Character character() default Character.NONE;

}
