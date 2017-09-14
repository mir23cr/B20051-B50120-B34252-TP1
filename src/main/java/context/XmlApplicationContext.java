package context;

import parsers.XmlParser;

/**
 * Created by Rodrigo on 9/9/2017.
 */
public  class XmlApplicationContext extends ApplicationContext {
    private XmlParser xmlParser;

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

    public void registerBeans() {

    }

    public void injectDependencies() {

    }

    public void close() {

    }
}
