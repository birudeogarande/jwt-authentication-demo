package com.saatvik.app.custom;


import com.saatvik.app.custom.WithMockCustomUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithCustomUser {
    String username() default "saatvik.garande@gmail.com";
    String name() default "Saatvik Garande";
    String authorities() default "ROLE_ADMIN";
}
