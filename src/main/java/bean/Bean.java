package bean;

import java.util.List;

import annotations.Component;
import annotations.Scope;

//@Component
public class Bean
{
    private String id;
    private Object instance;
    private String classType;
    private List<Class<?>> implementedInterfaces;
    private String init;
    private String destroy;
    private ScopeEnum scopeType;
    private List<Parameter> constructorArguments;
    private List<Parameter> properties;
    private Boolean lazyInit;


    public Bean() {
        this.scopeType = ScopeEnum.SINGLETON;
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

    public ScopeEnum getScopeType() {
        return this.scopeType;
    }

    public void setScopeType(ScopeEnum scopeType) {
        this.scopeType = scopeType;
    }

    public List<Parameter> getConstructorArguments() {
        return this.constructorArguments;
    }

    public void setConstructorArguments(List<Parameter> constructorArguments) {
        this.constructorArguments = constructorArguments;
    }

    public List<Parameter> getProperties() {
        return this.properties;
    }

    public void setProperties(List<Parameter> properties) {
        this.properties = properties;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(Boolean lazyInit) {
        this.lazyInit = lazyInit;
    }
}
