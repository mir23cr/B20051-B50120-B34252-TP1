package parsers;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import bean.Bean;
import bean.Scope;
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
    private Bean newBean;


    public XmlParser(){
        this.parser = new Builder();
        this.beansDefinition = new HashMap<String, Bean>();
    }

    public void getBeans(String fileName) throws ParsingException, IOException {

        Document document = parser.build(this.getClass().getClassLoader().getResourceAsStream(fileName));
        Element root = document.getRootElement();
        Elements children = root.getChildElements(ParserStringConstants.BEAN_LABEL);

        for (int i = 0; i < children.size(); i++) {
            this.createBean(children.get(i));
        }

    }

    public void createBean(Element beanDefinition){
        try{
            this.newBean = new Bean();
            Elements beanArgs;
            BeanProperty beanProperty;
            String propertyValue;
            for(int j = 0; j < beanDefinition.getAttributeCount(); j++) {
                beanProperty = BeanProperty.getProperty(beanDefinition.getAttribute(j).getLocalName());
                propertyValue = beanDefinition.getAttribute(j).getValue();
                switch (beanProperty){
                    case ID:
                        System.out.print("Se vio un id ");
                        newBean.setId(propertyValue);
                        break;
                    case CLASS:
                        System.out.print("Se vio un class ");
                        newBean.setClassType(propertyValue);
                        break;
                    case INIT:
                        System.out.print("Se vio un init ");
                        newBean.setInit(propertyValue);
                        break;
                    case DESTROY:
                        System.out.print("Se vio un destroy ");
                        newBean.setDestroy(propertyValue);
                        break;
                    case SCOPE:
                        System.out.print("Se vio un scope ");
                        newBean.setScopeType(Scope.valueOf(propertyValue.toUpperCase()));
                        break;
                    default:
                        System.out.print("Error");
                        break;
                }
                System.out.println(beanDefinition.getAttribute(j).getValue());
            }

            beanArgs = beanDefinition.getChildElements();
            getBeanArgs(beanArgs);
        }catch (Exception e){
            e.printStackTrace();
        }
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
