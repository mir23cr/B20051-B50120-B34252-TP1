package tests;


import annotations.Component;
import annotations.ScopeAnnotation;
import parsers.AnnotationParser;

/**
 * Created by Rodrigo on 9/9/2017.
 */
@Component
@ScopeAnnotation("prototype")
public class Test {
    
    public static void main(String[] args){
        AnnotationParser ap = new AnnotationParser("");

    }


}
