package enums;


/**
 * Enum with the attributes that bean could have.
 * @author Vladimir Aguilar
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
        switch (propertyName){
            case "id":
                property = ID;
                break;
            case "class":
                property = CLASS;
                break;
            case "init-method":
                property = INIT;
                break;
            case "destroy-method":
                property = DESTROY;
                break;
            case "scope":
                property = SCOPE;
                break;
            case "lazy-init":
                property = LAZYINIT;
                break;
        }

        return property;
    }
}
