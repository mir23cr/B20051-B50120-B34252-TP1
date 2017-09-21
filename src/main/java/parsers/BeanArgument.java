package parsers;

public enum BeanArgument {
    CONSTRUCTOR_ARG,
    PROPERTY,
    ERROR;

    public static BeanArgument getArgument(String argumentName){
        argumentName = argumentName.toLowerCase();
        BeanArgument argument = ERROR;
        if(argumentName.equals("constructor-arg")){
            argument = CONSTRUCTOR_ARG;
        }else if(argumentName.equals("property")){
            argument = PROPERTY;
        }
        return argument;
    }
}
