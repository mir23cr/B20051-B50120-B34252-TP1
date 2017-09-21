package context;

import bean.Bean;
import bean.Scope;
import parsers.AnnotationParser;
import parsers.XmlParser;

import java.util.Map;

public class AnnotationApplicationContext extends ApplicationContext {
    private AnnotationParser annotationParser;

    public AnnotationApplicationContext(String fileName){
        try {
            this.annotationParser = new AnnotationParser(fileName);
            this.defaultInit = this.annotationParser.getDefaultInitMethod();
            this.defaultDestroy = this.annotationParser.getDefaultDestroyMethod();
            this.registerBeans();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Seteo instancias*/
    public void registerBeans() {
        try {
            this.container = this.annotationParser.getBeans();
            this.setBeanSettings();
            this.injectDependencies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
