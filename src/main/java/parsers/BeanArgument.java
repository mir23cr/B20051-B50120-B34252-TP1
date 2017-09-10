package parsers;

public enum BeanArgument {
    CONSTRUCTOR_ARG,
    PROPERTY,
    BEAN,
    ERROR;

    public static BeanArgument getArgument(String argumentName){
        argumentName = argumentName.toLowerCase();
        BeanArgument property = ERROR;
        if(argumentName.equals("constructor-arg")){
            property = CONSTRUCTOR_ARG;
        }else if(argumentName.equals("property")){
            property = PROPERTY;
        }else if(argumentName.equals("bean")){
            property = BEAN;
        }
        return property;
    }
}
