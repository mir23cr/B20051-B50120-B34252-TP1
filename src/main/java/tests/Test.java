package tests;


import annotations.Component;
import annotations.Qualifier;
import annotations.Scope;
import parsers.AnnotationParser;

import java.net.URL;

/**
 * Created by Rodrigo on 9/9/2017.
 */
@Component
@Scope("prototype")
public class Test {
    
    public static void main(String[] args){
        AnnotationParser ap = new AnnotationParser("");

    }


}
