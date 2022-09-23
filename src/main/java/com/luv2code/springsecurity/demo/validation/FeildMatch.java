package com.luv2code.springsecurity.demo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = FieldMatchValidator.class)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeildMatch {
	String message() default "";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] playLoad() default{}; 
	
	String first();
	String second();
	
	@Target ({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List
	{
		FeildMatch[] value();
	}
}
