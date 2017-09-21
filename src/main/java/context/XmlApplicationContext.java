package context;

import bean.Bean;
import bean.Scope;
import parsers.XmlParser;
import java.util.Map;


public  class XmlApplicationContext extends ApplicationContext {
    private XmlParser xmlParser;

    public XmlApplicationContext(String fileName){
        try {
            this.xmlParser = new XmlParser(fileName);
            this.defaultInit = this.xmlParser.getDefaultInitMethod();
            this.defaultDestroy = this.xmlParser.getDefaultDestroyMethod();
            this.registerBeans();
            //this.injectDependencies();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Seteo instancias*/
    public void registerBeans() {
        try {
            this.container = this.xmlParser.getBeans();
            this.setBeanSettings();
            this.injectDependencies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
