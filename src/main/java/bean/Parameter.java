package bean;

import java.util.Comparator;

public class  Parameter {
    private Integer index;
    private String beanRef;
    private String name;
    private Object instance;
    private AutowireMode autowireMode;


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
}
