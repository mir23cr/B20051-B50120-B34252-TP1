package context;

public interface ApplicationContextInterface
{
    void registerBeans();
    void injectDependencies();
    void close();
}
