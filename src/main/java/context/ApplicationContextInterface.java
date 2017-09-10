package context;

public interface ApplicationContextInterface
{
    Object getBean(String beanId);
    <T> T getBean(Class<T> classType, String beanId);
    boolean containsBean(String beanId);
    boolean isSingleton(String beanId);
    boolean isPrototype(String beanId);
    void registerBeans();
    void injectDependencies();
    void close();
}
