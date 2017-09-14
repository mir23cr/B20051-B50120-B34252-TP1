package bean;

import java.util.List;
import javafx.util.Pair;

public class Bean
{
    String id;
    Object instance;
    String classType;
    List<Class<?>> implementedInterfaces;
    String init;
    String destroy;
    AutowireMode autowireMode;
    Scope scopeType;
    List<Parameter> constructorArguments;
    List<Pair<String,Parameter>> properties;

    public Bean() {
        this.autowireMode = AutowireMode.NO;
        this.scopeType = Scope.SINGLETON;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getInstance() {
        return this.instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public String getClassType() {
        return this.classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public List<Class<?>> getImplementedInterfaces() {
        return this.implementedInterfaces;
    }

    public void setImplementedInterfaces(List<Class<?>> implementedInterfaces) {
        this.implementedInterfaces = implementedInterfaces;
    }

    public String getInit() {
        return this.init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getDestroy() {
        return this.destroy;
    }

    public void setDestroy(String destroy) {
        this.destroy = destroy;
    }

    public AutowireMode getAutowireMode() {
        return this.autowireMode;
    }

    public void setAutowireMode(AutowireMode autowireMode) {
        this.autowireMode = autowireMode;
    }

    public Scope getScopeType() {
        return this.scopeType;
    }

    public void setScopeType(Scope scopeType) {
        this.scopeType = scopeType;
    }

    public List<Parameter> getConstructorArguments() {
        return this.constructorArguments;
    }

    public void setConstructorArguments(List<Parameter> constructorArguments) {
        this.constructorArguments = constructorArguments;
    }

    public List<Pair<String, Parameter>> getProperties() {
        return this.properties;
    }

    public void setProperties(List<Pair<String, Parameter>> properties) {
        this.properties = properties;
    }
}
