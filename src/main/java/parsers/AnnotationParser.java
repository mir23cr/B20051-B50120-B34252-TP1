package parsers;

import annotations.*;
import enums.AutowireMode;
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
import enums.ScopeEnum;
import sun.reflect.annotation.AnnotationSupport;

/**
 * Esta clase se encarga de analizar el paquete cuya dirección recibe como parámetro del constructor e
 * identificar las clases que estan anotadas con la anotación @Component. De encontrarse clases con esta
 * anotación procede a crear un Bean, asociarle un identificador y guardarlos en un mapa. Este mapa será luego
 * utilizado por  AnnotationApplicationContext.
 *
 * @author Rodrigo Acuña
 * @author Vladimir Aguilar
 * @author José Mesén
 * Creation Date: 9/9/2017
 */
public class AnnotationParser implements Parser {
    private String basePackagePath;
    private String userPackageSpecification;
    private Map<String, Bean> beans;

    /**
     * El constructor recibe el paquete que se desea analizar como parámetro.
     * Encuentra el directorio padre "classes" y agrega la extensión recibida, finalmente llama al método que scanea el
     * paquete y crea los Beans.
     * Escribe un mensaje de error si la dirección de directorio ingresado no es un directorio valido o no existe.
     *
     * @param packageLocation
     */
    public AnnotationParser(String packageLocation) {
        userPackageSpecification = packageLocation;
        //linea de stack overflow
        String classesRootDirectory = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        classesRootDirectory = classesRootDirectory.replace("%20", " ");

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

    /**
     * Este método recorre recursivamente el paquete con clases desde la raíz ingresada en el constructor.
     * Cada vez que se encuentra una clase llama al método scanClass(), que posiblemente genera un Bean si la clase esta
     * anotada, de ser así el Bean se agrega al contenedor de la clase.
     *
     * @param currentPackage
     */
    public void scanPackage(File currentPackage) {

        File[] packageContents = currentPackage.listFiles();
        for (File file : packageContents) {
            if (file.isDirectory())
                scanPackage(file);
            else {
                String className = file.getName().replace(".class", "");
                String classPath = "";
                String currentOs = System.getProperty("os.name").split(" ")[0];
                if (userPackageSpecification.length() > 0 && userPackageSpecification.compareTo(".") != 0)
                    classPath += userPackageSpecification + ".";
                if (currentPackage.getAbsolutePath() != basePackagePath) {
                    String currentPath = currentPackage.getPath().substring(basePackagePath.length() + 1);
                    if (currentOs.equals("Windows")) {
                        classPath += currentPath.replace("\\", ".") + "." + className;
                    } else {
                        classPath += currentPath.replace("/", ".") + "." + className;
                    }
                } else
                    classPath = userPackageSpecification + "." + className;
                Bean currentBean = scanClass(classPath);
                if (currentBean != null)
                    beans.put(currentBean.getId(), currentBean);
            }
        }

    }

    /**
     * Este método recibe una clase del método scanPackage y en primera instancia revisa si contiene una anotación de
     *
     * @param classPath
     * @return
     * @Component, de ser así este método llama otros métodos que la asisten a ingresar los metadatos del Bean necesarios
     * para luego poder consturir objetos e inyectar dependecias. Finalmente retorna el Bean con los metadatos debidamente almacenados.
     */
    private Bean scanClass(String classPath) {
        Bean bean = null;
        try {
            Class currentClass = Class.forName(classPath);
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

    /**
     * Este método se encarga de revisar la anotación de componente que tiene la clase para verificar si se ingresó
     * un beanId. Si es así lo devuelve, de lo contario genera uno con el nombre de la clase y la primer letra en minúscula
     * si solamente la primer letra de la clase es mayúscula. Ej: CatClass -> catClass, CATClass -> CATClass.
     *
     * @param currentClass
     * @return beanId of the scanned Class
     */
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

    /**
     * Este método analiza la clase y devuelve el Scope que va a tener el Bean.
     * Por defecto si no encuentra la anotación @Scope  devuelve SINGLETON.
     * Si la antocación esta presente verifica si el valor es PROTOTYPE y lo devuelve.
     *
     * @param currentClass
     * @return Scope of the scanned class.
     */
    private ScopeEnum getScope(Class currentClass) {
        ScopeEnum scopeEnum = ScopeEnum.SINGLETON;
        Scope scope = (Scope) currentClass.getAnnotation(Scope.class);
        if (scope != null && scope.value() == ScopeEnum.PROTOTYPE)
            scopeEnum = ScopeEnum.PROTOTYPE;
        return scopeEnum;
    }

    /**
     * Este método busca si la anotación @Lazy está presente en la clase y devuelve true de ser el caso.
     * Si la anotación no esta presente el Objeto del Bean se inicializa apenas se registra en el contendor.
     *
     * @param currentClass
     * @return True si el usuario desea realizar lazyInit.
     */
    private boolean getLazy(Class currentClass) {
        boolean lazy = false;
        Lazy lazyInit = (Lazy) currentClass.getAnnotation(Lazy.class);
        if (lazyInit != null)
            lazy = true;
        return lazy;
    }

    /**
     * Este método analiza los constructores de una clase e identifica si alguno tiene la anotación @Autowired.
     * Si esta anotación esta presente indica que se desea realizar inyección de dependencias por Constructor.
     * Se procedede a crear una lista con los parámetros recibidos por el constructor, almacenando su número, nombre,
     * tipo de clase y tipo de inyección deseada(BYNAME, BYTYPE), de ser BYNAME se almacena la referencia al Bean también.     *
     * Si más de un constructor tiene la anotación @Autowired, se considera solamente el primero.
     *
     * @param currentClass
     * @return Lista de Dependencias a inyectar por constructor
     */
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
                    } else {
                        constructorArgument.setAutowireMode(AutowireMode.BYTYPE);
                    }
                    arguments.add(constructorArgument);
                }
            }
            if (foundConstructor)
                break;
        }
        return arguments;
    }

    /**
     * Este método es llamado al encontrarse un método anotado con la anotación @Autowire.
     * Esto indica que se desea inyectar una dependecia mediante un método setter.
     * Se construyen los metadatos para poder inyectar la dependencia correctamente.
     * Se almacena el Nombre del parametro, el tipo de clase y el método de inyección.
     * De ser inyección por nombre AutowireMode.BYNAME indicado por la anotación @Qualifier,
     * se almacena la referencia al Bean.
     * Si el método posee más de un parámetro, se considera solo el primero que se encuentra.
     * @param method
     * @return Dependencia a inyectar por setter.
     */
    private Parameter getParameter(Method method) {
        java.lang.reflect.Parameter[] methodParameters = method.getParameters();
        Parameter parameter = new Parameter();
        if (methodParameters.length == 1) {
            java.lang.reflect.Parameter methodParameter = methodParameters[0];
            parameter.setIndex(0);
            parameter.setName(methodParameter.getName());
            parameter.setClassTypeName(methodParameter.getType().getCanonicalName());
            Qualifier qualifier = methodParameter.getAnnotation(Qualifier.class);
            if (qualifier != null) {
                parameter.setAutowireMode(AutowireMode.BYNAME);
                parameter.setBeanRef(qualifier.value());
            } else {
                parameter.setAutowireMode(AutowireMode.BYTYPE);
            }
        } else {
            System.out.println("Setter has incorrect number of parameters");
        }
        return parameter;
    }



    /**
     * Este método devuleve el contenedor con los Beans encontrados al escanear el paquete y con los metadatos ya guardados.
     * @return
     */
    @Override
    public Map<String, Bean> getBeans() {
        //printContainer(beans);
        return beans;
    }


    //Métodos auxiliares utilizados para depurar el programa.
    /**
     * Metodo de prueba, imprime el contenedor.
     * @param container
     */
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

    /**
     * Método de prueba, imprime argumentos de un constructor o método.
     * @param constructorArguments
     */
    private void printParameters(List<Parameter> constructorArguments) {
        for (Parameter constructorArgument : constructorArguments) {
            System.out.println(constructorArgument);

        }
    }
}
