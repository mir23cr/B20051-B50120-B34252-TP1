package tests;


import annotations.Component;
import annotations.Scope;
import parsers.AnnotationParser;

/**
 * Created by Rodrigo on 9/9/2017.
 */
@Component
public class Test {
    
    public static void main(String[] args){
        AnnotationParser ap = new AnnotationParser("tests.test1");
        ap.getBeans();

    }


}
