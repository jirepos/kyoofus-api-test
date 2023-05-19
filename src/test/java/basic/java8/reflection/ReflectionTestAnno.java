package basic.java8.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Reflection Test를 위한 Annotation 이다. */
@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.TYPE);
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface ReflectionTestAnno {
    public String name();
    public String value();
}