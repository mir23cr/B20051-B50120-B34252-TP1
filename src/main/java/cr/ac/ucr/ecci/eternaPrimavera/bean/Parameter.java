package cr.ac.ucr.ecci.eternaPrimavera.bean;

import cr.ac.ucr.ecci.eternaPrimavera.enums.AutowireMode;

/**
 * Class that stores the meta-data for the setters and constructor arguments.
 * Also includes the instance if the Bean is Singleton.
 * @author Vladimir Aguilar
 * @author Jose Mesén
 * @author Rodrigo Acuña
 */
public class Parameter {
    private Integer index;
    private String name;
    private String classTypeName;
    private String beanRef;
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

    @Override
    public String toString(){
        return index + "\t" + classTypeName + "\t" + name + "\n" +autowireMode + "\t" +beanRef;
    }
}
