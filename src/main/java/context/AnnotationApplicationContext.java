package context;

import parsers.AnnotationParser;

public class AnnotationApplicationContext extends ApplicationContext {
    private AnnotationParser annotationParser;

    public AnnotationApplicationContext(String fileName){
        try {
            this.annotationParser = new AnnotationParser(fileName);
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
