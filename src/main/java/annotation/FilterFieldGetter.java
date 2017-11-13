package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

@Inherited
@Target(ElementType.METHOD)
public @interface FilterFieldGetter {
    String value();
}
