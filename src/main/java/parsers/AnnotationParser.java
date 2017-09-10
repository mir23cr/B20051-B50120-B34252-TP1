package parsers;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;

/**
 * @author Rodrigo Acuña
 * @author Vladimir Aguilar
 * @author José Mesén
 * Creation Date: 9/9/2017
 */
public class AnnotationParser implements Parser {

    public AnnotationParser(String packageLocation){
        //linea
        // String classesRootDirectory = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        //String basePackagePath = classesRootDirectory+packageLocation.replaceAll(".","/");
        File basePackage = new File("src/main/java/"+packageLocation);
        System.out.println(basePackage.getAbsolutePath());

        if(!basePackage.exists() || !basePackage.isDirectory())
            System.out.println("Incorrect Base Package");
        else {
            try {
                scanPackage(basePackage);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void scanPackage(File currentPackage) throws ClassNotFoundException, IOException {
        File[] packageContents = currentPackage.listFiles();
        for(File file: packageContents){
            if(file.isDirectory())
                scanPackage(file);
            else{
                String className = file.getName().replace(".java","");
                String claseFinal = currentPackage.getPath()+"\\"+className;
                System.out.println(claseFinal);

                Class clase = Class.forName(claseFinal.replace('\\','.').replace("src.main.java.",""));
                Annotation[] annotations = clase.getAnnotations();
                for (Annotation annotation: annotations){
                    System.out.println(annotation);
                }

            }
        }

    }




}
