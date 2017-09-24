package parsers;

import annotations.*;
import bean.AutowireMode;
import bean.Bean;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bean.Parameter;
import bean.ScopeEnum;

/**
 * @author Rodrigo Acuña
 * @author Vladimir Aguilar
 * @author José Mesén
 * Creation Date: 9/9/2017
 */
public class AnnotationParser implements Parser {
    private String basePackagePath;
    private String userPackageSpecification;
    private Map<String,Bean> beans;

    public AnnotationParser(String packageLocation) {
        userPackageSpecification = packageLocation;
        //linea de stack overflow
        String classesRootDirectory = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        classesRootDirectory = classesRootDirectory.replace("%20"," ");

        File basePackage = new File(classesRootDirectory + packageLocation.replace(".", "/"));
        basePackagePath = basePackage.getAbsolutePath();

        System.out.println(basePackagePath);

        if (!basePackage.exists() || !basePackage.isDirectory())
            System.out.println("Incorrect Base Package");
        else {
            beans = new HashMap<>();
            scanPackage(basePackage);
        }
    }

    public void scanPackage(File currentPackage) {

        File[] packageContents = currentPackage.listFiles();
        for (File file : packageContents) {
            if (file.isDirectory())
                scanPackage(file);
            else {
                String className = file.getName().replace(".class", "");
                String classPath = "";
                if(userPackageSpecification.length()>0 && userPackageSpecification.compareTo(".") !=0)
                    classPath += userPackageSpecification +".";
                if (currentPackage.getAbsolutePath() != basePackagePath) {
                    String currentPath = currentPackage.getPath().substring(basePackagePath.length() + 1);
                    classPath += currentPath.replace("\\", ".") + "." + className;
                } else
                    classPath = userPackageSpecification +"."+ className;
                Bean currentBean = scanClass(classPath);
                if (currentBean != null)
                    beans.put(currentBean.getId(), currentBean);
            }
        }

    }

    private Bean scanClass(String classPath) {
        Bean bean = null;
        try {
            Class currentClass = Class.forName(classPath);
            //System.out.println(currentClass.getSimpleName());

            //Revisar si es un componente
            if (currentClass.isAnnotationPresent(Component.class)) {
                //Se crea el bean.
                bean = new Bean();
                //Asignar class
                bean.setClassType(classPath);
                //Asignar bean id
                String id = getBeanId(currentClass);
                bean.setId(id);
                //Asignar scope
                ScopeEnum scope = getScope(currentClass);
                bean.setScopeType(scope);
                //Asinar lazyInit
                bean.setLazyInit(getLazy(currentClass));
                //Asignar Parametros de Constructor SOLO SE PUEDE UN CONSTRUCTOR CON AUTOWIRE
                List<Parameter> constructorArguments = getConstructorArguments(currentClass);
                bean.setConstructorArguments(constructorArguments);
                //Asignar propiedades de setters, init-method y destroy-method
                Method[] methods = currentClass.getMethods();
                List<Parameter> beanProperties = new LinkedList<>();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Autowired.class)) {
                        beanProperties.add(getParameter(method));
                    } else if (method.isAnnotationPresent(PostConstruct.class)) {
                        bean.setInit(method.getName());
                    } else if (method.isAnnotationPresent(PreDestroy.class)) {
                        bean.setDestroy(method.getName());
                    }
                }
                bean.setProperties(beanProperties);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Invalid file: " + classPath);
        }
        return bean;
    }

    private String getBeanId(Class currentClass) {
        Component componentAnnotation = (Component) currentClass.getAnnotation(Component.class);
        String annotationValue = componentAnnotation.value();
        String className = currentClass.getSimpleName();
        String result;
        if (annotationValue.compareTo("null") != 0)
            result = annotationValue;
        else {
            char firstLetter = className.charAt(0);
            char secondLetter = className.charAt(1);
            if (Character.isUpperCase(firstLetter) && Character.isLowerCase(secondLetter))
                result = Character.toLowerCase(firstLetter) + className.substring(1);
            else
                result = className;
        }
        return result;
    }

    private ScopeEnum getScope(Class currentClass) {
        ScopeEnum scopeEnum = ScopeEnum.SINGLETON;
        Scope scope = (Scope) currentClass.getAnnotation(Scope.class);
        if (scope != null && scope.value().toLowerCase().compareTo("prototype") == 0)
            scopeEnum = ScopeEnum.PROTOTYPE;
        return scopeEnum;
    }

    private boolean getLazy(Class currentClass) {
        boolean lazy = false;
        Lazy lazyInit = (Lazy) currentClass.getAnnotation(Lazy.class);
        if (lazyInit != null)
            lazy = true;
        return lazy;
    }

    public List<Parameter> getConstructorArguments(Class currentClass) {
        List<Parameter> arguments = new LinkedList<>();
        Constructor[] constructors = currentClass.getDeclaredConstructors();
        boolean foundConstructor = false;
        for (Constructor constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                foundConstructor = true;
                java.lang.reflect.Parameter[] constructorParameters = constructor.getParameters();
                for (int i = 0; i < constructorParameters.length; i++) {
                    java.lang.reflect.Parameter currentParameter = constructorParameters[i];
                    Parameter constructorArgument = new Parameter();
                    constructorArgument.setIndex(i);
                    constructorArgument.setName(currentParameter.getName());
                    constructorArgument.setClassTypeName(currentParameter.getType().getCanonicalName());
                    Qualifier qualifier = currentParameter.getAnnotation(Qualifier.class);
                    if (qualifier != null) {
                        constructorArgument.setAutowireMode(AutowireMode.BYNAME);
                        constructorArgument.setBeanRef(qualifier.value());
                    }
                    else{
                        constructorArgument.setAutowireMode(AutowireMode.BYTYPE);
                    }
                    arguments.add(constructorArgument);
                }
            }
            if(foundConstructor)
                break;
        }
        return arguments;
    }

    private Parameter getParameter(Method method) {
        java.lang.reflect.Parameter[] methodParameters = method.getParameters();
        Parameter parameter = new Parameter();
        if(methodParameters.length == 1){
            java.lang.reflect.Parameter methodParameter = methodParameters[0];
            parameter.setIndex(0);
            parameter.setName(methodParameter.getName());
            parameter.setClassTypeName(methodParameter.getType().getCanonicalName());
            Qualifier qualifier = methodParameter.getAnnotation(Qualifier.class);
            if (qualifier != null) {
                parameter.setAutowireMode(AutowireMode.BYNAME);
                parameter.setBeanRef(qualifier.value());
            }
            else {
                parameter.setAutowireMode(AutowireMode.BYTYPE);
            }
        }
        else{
            System.out.println("Setter has incorrect number of parameters");
        }
        return parameter;
    }


    @Override
    public Map<String, Bean> getBeans() {
        //printContainer(beans);
        return beans;
    }

    public void printContainer(Map<String, Bean> container){
        Bean bean;
        for (Map.Entry<String,Bean> element : container.entrySet()){
            System.out.println("Bean id: " + element.getKey());
            bean = element.getValue();
            System.out.println(bean.getScopeType());
            System.out.println("Tipo de clase: " + bean.getClassType());
            List<Parameter> constructorArguments = bean.getConstructorArguments();
            System.out.println("Argumentos del constructor: " + constructorArguments.size());
            printParameters(constructorArguments);
            List<Parameter> properties = bean.getProperties();
            System.out.println("Argumentos de las propiedades: " + properties.size());
            printParameters(properties);
            System.out.println("Método de init: " + bean.getInit());
            System.out.println("Método de destroy: " + bean.getDestroy());
            System.out.println();
        }

    }

    private void printParameters(List<Parameter> constructorArguments) {
        for (Parameter constructorArgument : constructorArguments) {
            System.out.println(constructorArgument);

        }
    }
}
