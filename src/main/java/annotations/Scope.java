package annotations;


import enums.ScopeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación que indica el Scopoe que tendrá el Bean, el cual se alamacena en el valor de la anotación.
 * Por defecto el valor es Singleton es decir una sola referencia a todos los llamados a este bean
 * Tambien es posible especificar PROTOTYPE que indica que se desea un nuevo objeto con cada llamado a getBean()
 * Se debe utilizar encima de la declaración de la una clase anotada con @Component
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scope {
    ScopeEnum value() default ScopeEnum.SINGLETON;
}
