package net.atos.common.validators;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
 
import javax.validation.Constraint;
import javax.validation.Payload;
 
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NifValidator.class)
@Documented
public @interface Nif {
 
	String message() default "Formato de Nif incorrecto";
 
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}