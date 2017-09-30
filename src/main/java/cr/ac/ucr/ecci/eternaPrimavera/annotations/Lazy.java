package cr.ac.ucr.ecci.eternaPrimavera.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que indica que se desea incializar el objeto de este Bean hasta que se solicite el mismo,
 * de lo contario se incializa en el momento que se registra en el contenedor.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Lazy {
}
