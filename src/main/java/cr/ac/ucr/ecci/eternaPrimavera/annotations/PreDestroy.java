package cr.ac.ucr.ecci.eternaPrimavera.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Antoación que indica que se desea llamar a este método antes de destruir el objeto para realizar algún
 * trabajo adicional.
 * Se debe utilizar la anotación encima de un método.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PreDestroy {
}
