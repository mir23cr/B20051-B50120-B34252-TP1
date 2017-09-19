package context;

import bean.Bean;
import bean.Parameter;
import bean.Scope;
import jdk.nashorn.internal.objects.annotations.Property;
import nu.xom.ParsingException;
import parsers.BeanProperty;
import parsers.Parser;
import parsers.XmlParser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by Rodrigo on 9/9/2017.
 */
public  class XmlApplicationContext extends ApplicationContext {
    private XmlParser xmlParser;

    public XmlApplicationContext(String fileName){
        try {
            this.xmlParser = new XmlParser(fileName);
            this.defaultInit = this.xmlParser.getDefaultInitMethod();
            this.defaultDestroy = this.xmlParser.getDefaultDestroyMethod();
            this.registerBeans();
            //this.injectDependencies();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Object getBean(String beanId) {
        return null;
    }

    public <T> T getBean(Class<T> classType, String beanId) {
        return null;
    }

    public boolean containsBean(String beanId) {
        return false;
    }

    public boolean isSingleton(String beanId) {
        return false;
    }

    public boolean isPrototype(String beanId) {
        return false;
    }

    /*Seteo instancias*/
    public void registerBeans() {
        try {
            Class classToInstance;
            Object objectToInstance;
            this.container = this.xmlParser.getBeans();
            Bean bean;
            for (Map.Entry<String,Bean> element : container.entrySet()){
                bean = element.getValue();
                if(bean.getInit()==null){
                    bean.setInit(this.defaultInit);
                }
                if(bean.getDestroy()==null){
                    bean.setDestroy(this.defaultDestroy);
                }
                if(bean.getScopeType() == Scope.SINGLETON){
                    classToInstance = Class.forName(bean.getClassType());
                    objectToInstance = classToInstance.newInstance();
                    bean.setInstance(objectToInstance);
                }
            }
            this.injectDependencies();
            //this.printContainer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void injectDependencies() {
        try {
            Class classToInstance;
            Class objectClass;
            Object objectToInstance;
            Object instancedObject;
            Field fieldToInstance;
            Bean bean;
            Bean beanToInstance;
            for (Map.Entry<String,Bean> element : container.entrySet()){
                bean = element.getValue();
                if(bean.getScopeType() == Scope.SINGLETON){
                    instancedObject = bean.getInstance();
                    objectClass = instancedObject.getClass();
                    for(Parameter p : bean.getProperties()) {
                        beanToInstance = this.container.get(p.getBeanRef());
                        if(beanToInstance.getScopeType() == Scope.SINGLETON){
                            objectToInstance = beanToInstance.getInstance();
                            fieldToInstance = objectClass.getDeclaredField(p.getName());
                            fieldToInstance.setAccessible(true);
                            fieldToInstance.set(instancedObject,objectToInstance);
                        }else{
                            this.getBean(p.getBeanRef());
                        }
                    }
                }

            }
            //this.printContainer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {

    }

    private void printContainer(){
        Bean bean;
        for (Map.Entry<String,Bean> element : container.entrySet()){
            System.out.println("Bean id: " + element.getKey());
            bean = element.getValue();
            System.out.println("Tipo de clase: " + bean.getClassType());
            System.out.println("Modo de autowire: " + bean.getAutowireMode());
            System.out.println("Argumentos del constructor: " + bean.getConstructorArguments().size());
            System.out.println("Argumentos de las propiedades: " + bean.getProperties().size());
            System.out.println("Tamaño de las dependencias de beans: " + bean.getBeanDependencies().size());
            System.out.println("Método de init: " + bean.getInit());
            System.out.println("Método de destroy: " + bean.getDestroy());
            System.out.println();
        }

    }
}
