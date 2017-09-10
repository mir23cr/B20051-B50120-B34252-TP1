package bean;

import java.util.List;
import javafx.util.Pair;

public class Bean
{
    String Id;
    Object instance;
    Class classType;
    List<Class<?>> implementedInterfaces;
    String init;
    String destroy;
    AutowireMode autowireMode;
    Scope scopeType;
    List<Parameter> constructorArguments;
    List<Pair<String,Parameter>> properties;

}
