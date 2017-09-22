package parsers;

import annotations.Component;
import annotations.ScopeAnnotation;
import bean.Bean;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import bean.Scope;

/**
 * @author Rodrigo Acuña
 * @author Vladimir Aguilar
 * @author José Mesén
 *         Creation Date: 9/9/2017
 */
public class AnnotationParser implements Parser {
    String basePackagePath;

    public AnnotationParser(String packageLocation) {
        //linea de stack overflow
        String classesRootDirectory = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        File basePackage = new File(classesRootDirectory + packageLocation.replaceAll(".", "/"));
        basePackagePath = basePackage.getAbsolutePath();
        System.out.println(basePackagePath);

        if (!basePackage.exists() || !basePackage.isDirectory())
            System.out.println("Incorrect Base Package");
        else {
            scanPackage(basePackage);
        }
    }

    public void scanPackage(File currentPackage) {
        File[] packageContents = currentPackage.listFiles();
        Map<String, Bean> context = new HashMap<>();
        for (File file : packageContents) {
            if (file.isDirectory())
                scanPackage(file);
            else {
                String className = file.getName().replace(".class", "");
                String classPath;
                if (currentPackage.getAbsolutePath() != basePackagePath) {
                    String currentPath = currentPackage.getPath().substring(basePackagePath.length() + 1);
                    classPath = currentPath.replace("\\", ".") + "." + className;
                } else
                    classPath = className;
                Bean currentBean = scanClass(classPath);
                if (currentBean != null)
                    context.put(currentBean.getId(), currentBean);

            }
        }

    }

    private Bean scanClass(String classPath) {
        Bean bean = null;
        try {
            Class currentClass = Class.forName(classPath);
            System.out.println(currentClass.getSimpleName());
            Component componentAnnotation = (Component) currentClass.getAnnotation(Component.class);
            //Revisar si es un componente
            if (componentAnnotation != null) {
                bean = new Bean();
                //Asignar bean id
                String id = getBeanId(componentAnnotation.value(), currentClass.getSimpleName());
                bean.setId(id);
                //Asignar scope
                ScopeAnnotation scopeAnnotation = (ScopeAnnotation) currentClass.getAnnotation(ScopeAnnotation.class);
                if (scopeAnnotation != null && scopeAnnotation.value().toLowerCase().compareTo("prototype") == 0)
                    bean.setScopeType(Scope.PROTOTYPE);

                //Asignar Parametros de Constructor SOLO SE PUEDE UN CONSTRUCTOR
                Constructor constructor = currentClass.getConstructors()[0];


                System.out.println("\t" + id);
                System.out.println("\t" + "tiene anotaciones");
                Annotation[] annotations = currentClass.getAnnotations();
                for (Annotation annotation : annotations) {
                    System.out.println("\t" + annotation);
                    //System.out.println("\t"+annotation.annotationType().getSimpleName());
                }

            }

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + classPath);
        }
        return bean;
    }

    private String getBeanId(String annotationValue, String className) {
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
    public String getDefaultInitMethod()  {
        return null;
    }

    public String getDefaultDestroyMethod()  {
        return null;
    }

    public Map<String, Bean> getBeans() {
        return null;
    }



}
