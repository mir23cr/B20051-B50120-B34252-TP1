package context;

import bean.Bean;

import java.util.Map;

public abstract class ApplicationContext implements ApplicationContextInterface
{
    protected Map<String, Bean> container;
    protected String defaultInit;
    protected String defaultDestroy;
}
