package parsers;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import bean.Bean;
import nu.xom.*;

/**
 * @author Rodrigo Acuña
 * @author Vladimir Aguilar
 * @author José Mesén
 * Creation Date: 9/9/2017
 */
public class XmlParser implements Parser {

    private Builder parser;
    private Map<String,Bean> beansDefinition;

    public XmlParser(){
        this.parser = new Builder();
        this.beansDefinition = new HashMap<String, Bean>();
    }

    public void getBeans(String fileName) throws ParsingException, IOException {
        Bean newBean;
        Document document = parser.build(this.getClass().getClassLoader().getResourceAsStream(fileName));
        Element root = document.getRootElement();
        Elements children = root.getChildElements(ParserStringConstants.BEAN_LABEL);

        for (int i = 0; i < children.size(); i++) {
            newBean = this.getBean(children.get(i));
        }

    }

    public Bean getBean(Element beanDefinition){
        Bean newBean = new Bean();
        Elements beanArgs;

        for(int j = 0; j < beanDefinition.getAttributeCount(); j++) {
            switch (BeanProperty.getProperty(beanDefinition.getAttribute(j).getLocalName())){
                case ID:
                    System.out.print("Se vio un id ");
                    break;
                case CLASS:
                    System.out.print("Se vio un class ");
                    break;
                case INIT:
                    System.out.print("Se vio un init ");
                    break;
                case DESTROY:
                    System.out.print("Se vio un destroy ");
                    break;
                case SCOPE:
                    System.out.print("Se vio un scope ");
                    break;
                default:
                    System.out.print("Error");
                    break;
            }
            System.out.println(beanDefinition.getAttribute(j).getValue());
        }

        beanArgs = beanDefinition.getChildElements();
        getBeanArgs(beanArgs);
        return newBean;
    }

    public void getBeanArgs(Elements beanArgs){
        Element argument;
        for(int i =0; i < beanArgs.size(); i++){
            argument = beanArgs.get(i);
            switch (BeanArgument.getArgument(argument.getLocalName())){
                case CONSTRUCTOR_ARG:
                    System.out.println("Varas del constructor ");
                    this.getConstructorInformation(argument);
                    break;
                case PROPERTY:
                    System.out.println("Varas de properties ");
                    this.getPropertiesInformation(argument);
                    break;
                case BEAN:
                    System.out.println("Bean anidado ");
                    break;
                case ERROR:
                    System.out.println("Error!");
                    break;
            }
        }
    }

    private void getConstructorInformation(Element info){
        Attribute attribute;
        for (int i = 0; i < info.getAttributeCount(); i++) {
            attribute = info.getAttribute(i);
            System.out.println(attribute.getLocalName() + " " + attribute.getValue());
        }
    }

    private void getPropertiesInformation(Element info){
        Attribute attribute;
        for (int i = 0; i < info.getAttributeCount(); i++) {
            attribute = info.getAttribute(i);
            System.out.println(attribute.getLocalName() + " " + attribute.getValue());
        }
    }

    public static void main(final String[] args)
    {
        try {
            XmlParser xmlParser = new XmlParser();
            xmlParser.getBeans("beans.xml");

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
