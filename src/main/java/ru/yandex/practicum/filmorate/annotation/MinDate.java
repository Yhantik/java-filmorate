package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinDate {
    String message() default "{MinDate.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String value();
}