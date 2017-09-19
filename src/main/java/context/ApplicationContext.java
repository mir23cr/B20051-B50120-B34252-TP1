package context;
import bean.Bean;
import bean.Scope;

import java.util.HashMap;
import java.util.Map;

public abstract class ApplicationContext implements ApplicationContextInterface
{
    protected Map<String,Bean> container;
    protected String defaultInit;
    protected String defaultDestroy;

    public ApplicationContext()
    {
        this.container = new HashMap<String, Bean>();
    }

    public Object getBean(String beanId) {

        return container.get(beanId);
    }

    public <T> T getBean(Class<T> classType, String beanId) {

        Bean bean = container.get(beanId);

        //If its singleton, then return a new instance
        if(bean.getScopeType() != Scope.SINGLETON)
        {
            try {
                return classType.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //Else, return the singleton object
        return classType.cast(container.get(beanId).getInstance());
    }

    public boolean containsBean(String beanId) {

        return container.containsKey(beanId);
    }

    public boolean isSingleton(String beanId) {

        if (container.get(beanId).getScopeType() == Scope.SINGLETON)
            return true;
        return false;
    }

    public boolean isPrototype(String beanId) {

        if (container.get(beanId).getScopeType() == Scope.PROTOTYPE)
            return true;
        return false;
    }
}
