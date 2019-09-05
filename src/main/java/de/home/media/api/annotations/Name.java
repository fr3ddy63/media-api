package de.home.media.api.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "[a-zA-Z0-9]{2,50}")
public @interface Name {

    String message() default "Invalid name.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
