package cr.ac.ucr.ecci.eternaPrimavera.context;

import cr.ac.ucr.ecci.eternaPrimavera.parsers.XmlParser;

/**
 * Application cr.ac.ucr.ecci.eternaPrimavera.context for XML based container.
 * @author Vladimir Aguilar
 * @author Jose Mesén
 */
public  class XmlApplicationContext extends ApplicationContext {
    private XmlParser xmlParser;

    public XmlApplicationContext(String fileName){
        try {
            this.xmlParser = new XmlParser(fileName);
            this.registerBeans();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Seteo instancias*/
    public void registerBeans() {
        try {
            this.container = this.xmlParser.getBeans();
            this.defaultInit = this.xmlParser.getDefaultInitMethod();
            this.defaultDestroy = this.xmlParser.getDefaultDestroyMethod();
            this.setBeanSettings();
            this.injectDependencies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
