package context;
import bean.Bean;
import bean.Parameter;
import bean.ScopeEnum;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public abstract class ApplicationContext implements ApplicationContextInterface
{
    protected Map<String,Bean> container;
    protected String defaultInit;
    protected String defaultDestroy;

    public Object getBean(String beanId) {
        Bean requestedBean = this.container.get(beanId);
        Object result = null;
        if(requestedBean != null){
            switch (requestedBean.getScopeType()){
                case SINGLETON:
                    result = requestedBean.getInstance();
                    if(result == null){
                        result = this.getNewBeanInstance(requestedBean);
                        requestedBean.setInstance(result);
                            /*Call the postConstructMethod*/
                        if(requestedBean.getInit() != null){
                            this.callInitMethodSingleton(requestedBean);
                        }
                        this.injectSetters(requestedBean.getProperties(),result);
                    }
                    break;
                case PROTOTYPE:
                    result = this.getNewBeanInstance(requestedBean);
                    if(requestedBean.getInit() != null) {
                        this.callInitMethodPrototype(requestedBean, result);
                    }
                    this.injectSetters(requestedBean.getProperties(),result);
                    break;
            }
        }

        return result;
    }

    public <T> T getBean(Class<T> classType, String beanId) {
        return classType.cast(this.getBean(beanId));
    }

    private Object getNewBeanInstance(Bean bean){
        try {
            Object newInstance;
            Class classToInstance;
            Constructor constructorToUse;
            Class[] constructorArgs;
            Object[] constructorParameters;
            int beanConstructorArgsCount = bean.getConstructorArguments().size();
            Bean beanToSet;
            /*Check the constructors*/
            classToInstance = Class.forName(bean.getClassType());
            if(beanConstructorArgsCount == 0){
                newInstance = classToInstance.newInstance();
            }else{

                constructorArgs = new Class[beanConstructorArgsCount];
                constructorParameters = new Object[beanConstructorArgsCount];
                for (int i = 0; i < beanConstructorArgsCount; i++) {
                    beanToSet = this.container.get(bean.getConstructorArguments().get(i).getBeanRef());
                    constructorArgs[i] = Class.forName(beanToSet.getClassType());
                    constructorParameters[i] = this.getBean(bean.getConstructorArguments().get(i).getBeanRef());
                }
                constructorToUse = classToInstance.getConstructor(constructorArgs);
                newInstance = constructorToUse.newInstance(constructorParameters);
            }
            return newInstance;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void injectSetters(List<Parameter> setters, Object object){
        try {
            Class refClass;
            Class setterClass;
            Method[] refClassMethods;
            Method refClassMethod;
            boolean foundMethod;
            int index;
            refClass = object.getClass();
            refClassMethods = refClass.getMethods();
            for(Parameter p: setters){
                foundMethod = false;
                index = 0;
                setterClass = Class.forName(this.container.get(p.getBeanRef()).getClassType());
                while(index < refClassMethods.length && !foundMethod){
                    refClassMethod = refClassMethods[index];
                    if(refClassMethod.getParameterTypes().length == 1 &&
                            refClassMethod.getParameterTypes()[0].getName().equals(setterClass.getName())){
                        foundMethod = true;
                        refClassMethod.invoke(object,this.getBean(p.getBeanRef()));
                    }else{
                        index ++;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void injectDependencies() {
        try {
            for (Map.Entry<String,Bean> element : container.entrySet()){
                if(element.getValue().getScopeType() == ScopeEnum.SINGLETON){
                    this.injectSetters(element.getValue().getProperties(),element.getValue().getInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callInitMethodSingleton(Bean bean){
        try {
            Method initMethod = bean.getInstance().getClass().getMethod(bean.getInit());
            initMethod.invoke(bean.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callInitMethodPrototype(Bean bean, Object object){
        try {
            Method initMethod = Class.forName(bean.getClassType()).getMethod(bean.getInit());
            initMethod.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void close() {
        try {
            Method destroyMethod;
            for (Map.Entry<String,Bean> element : container.entrySet()){
                if(element.getValue().getScopeType() == ScopeEnum.SINGLETON){
                    if(element.getValue().getDestroy()!= null){
                        destroyMethod = Class.forName(element.getValue().getClassType()).getMethod(element.getValue().getDestroy());
                        destroyMethod.invoke(element.getValue().getInstance());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printContainer(){
        Bean bean;
        for (Map.Entry<String,Bean> element : container.entrySet()){
            System.out.println("Bean id: " + element.getKey());
            bean = element.getValue();
            System.out.println("Tipo de clase: " + bean.getClassType());
            System.out.println("Modo de autowire: " + bean.getAutowireMode());
            System.out.println("Argumentos del constructor: " + bean.getConstructorArguments().size());
            System.out.println("Argumentos de las propiedades: " + bean.getProperties().size());
            System.out.println("Método de init: " + bean.getInit());
            System.out.println("Método de destroy: " + bean.getDestroy());
            System.out.println();
        }

    }

    public boolean containsBean(String beanId) {
        return container.containsKey(beanId);
    }

    public boolean isSingleton(String beanId) {
        boolean result = false;
        if (container.get(beanId).getScopeType() == ScopeEnum.SINGLETON)
            result = true;
        return result;
    }

    public boolean isPrototype(String beanId) {
        boolean result = false;
        if (container.get(beanId).getScopeType() == ScopeEnum.PROTOTYPE)
            result =  true;
        return result;
    }

    protected void setBeanSettings() throws Exception {
        Bean bean;
        for (Map.Entry<String,Bean> element : container.entrySet()){
            bean = element.getValue();
            if(bean.getInit()==null){
                bean.setInit(this.defaultInit);
            }
            if(bean.getDestroy()==null){
                bean.setDestroy(this.defaultDestroy);
            }
            /*Constructor analysis*/
            if(bean.getScopeType() == ScopeEnum.SINGLETON){
                bean.setInstance(this.getNewBeanInstance(bean));
                /*Call the postConstructMethod*/
                if(bean.getInit() != null){
                    this.callInitMethodSingleton(bean);
                }
            }
        }
    }
}
