package bean;

import java.util.Comparator;

public class Parameter {
    private Integer index;
    private String name;
    private String classTypeName;
    private String beanRef;
    private AutowireMode autowireMode;
    private Object instance;


    public Parameter() {
        this.autowireMode = AutowireMode.NO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getBeanRef() {
        return beanRef;
    }

    public void setBeanRef(String beanRef) {
        this.beanRef = beanRef;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public AutowireMode getAutowireMode() {
        return autowireMode;
    }

    public void setAutowireMode(AutowireMode autowireMode) {
        this.autowireMode = autowireMode;
    }

    public String getClassTypeName() {
        return classTypeName;
    }

    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }
}
