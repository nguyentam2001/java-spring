package com.luv2code.springsecurity.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FeildMatch,Object> {

	private String firstFieldName;
	private String secondFeildName;
	private String message;
	@Override
	public void initialize(final FeildMatch constraintAnnotation) {
		firstFieldName= constraintAnnotation.first();
		secondFeildName=constraintAnnotation.second();
		message=constraintAnnotation.message();
	}
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid=true;
		try {
			final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
			final Object secondObj= new BeanWrapperImpl(value).getPropertyValue(secondFeildName);
			
			valid=firstObj ==null && secondObj==null || firstObj!=null && firstObj.equals(secondObj);
		} catch (Exception e) {
			// we can ignore
		}
		
		if(!valid) {
			context.buildConstraintViolationWithTemplate(message).addPropertyNode(firstFieldName)
					.addConstraintViolation().disableDefaultConstraintViolation();
		}
		
		return false;
	}

}
