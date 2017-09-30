package annotations;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que indica que la inyección de una dependecia se desea hacer por nombre AutowireBYNAME.
 * Se almacena dentro de su valor la referencia al bean que se desea inyectar.
 * Se debe utilzar la anotación antes de un parametro
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Qualifier {
    @NotNull String value();
}
