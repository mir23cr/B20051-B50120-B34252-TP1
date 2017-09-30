package annotations;

import enums.ComponentEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que indica que se desea analizar esta clase y crear un Bean a partir de ella.
 * Es decir se desea que el contenedor maneje la creación de estos objetos y la inyección de sus dependencias.
 * Especifica que la clase es un servicio.
 * Se debe poner la antoación encima de la declaración de la clase
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Service {
    String value() default "null";
    ComponentEnum componentType() default ComponentEnum.SERVICE;
}
