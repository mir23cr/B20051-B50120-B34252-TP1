package parsers;

/**
 * Creation Date: 9/9/2017
 */
public enum BeanProperty {
    ID,
    CLASS,
    SCOPE,
    INIT,
    DESTROY,
    LAZYINIT,
    ERROR;

    public static BeanProperty getProperty(String propertyName){
        propertyName = propertyName.toLowerCase();
        BeanProperty property = ERROR;
        if(propertyName.equals("id")){
            property = ID;
        }else if(propertyName.equals("class")){
            property = CLASS;
        }else if(propertyName.equals("init-method")){
            property = INIT;
        }else if(propertyName.equals("destroy-method")){
            property = DESTROY;
        }else if(propertyName.equals("scope")){
            property = SCOPE;
        }else if(propertyName.equals("lazy-init")){
            property = LAZYINIT;
        }
        return property;
    }
}
