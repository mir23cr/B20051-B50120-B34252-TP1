package cr.ac.ucr.ecci.eternaPrimavera.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que indica se desea inyectar una dependencia.
 * Puede ser utilizada tanto en el constructor de una clase como en un método setter.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR,ElementType.METHOD})
public @interface Autowired {
}
