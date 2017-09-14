package parsers;


import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bean.AutowireMode;
import bean.Bean;
import bean.Parameter;
import bean.Scope;
import javafx.util.Pair;
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
    private String fileName;
    //private Bean newBean;


    public XmlParser(String fileName){
        this.fileName = fileName;
        this.parser = new Builder();
        this.beansDefinition = new HashMap<String, Bean>();
    }

    public String getDefaultInitMethod() throws ParsingException, IOException {
        Document document = parser.build(this.getClass().getClassLoader().getResourceAsStream(this.fileName));
        Element root = document.getRootElement();
        Attribute attribute = root.getAttribute(ParserStringConstants.BEANS_DEFAULT_INIT);
        return (attribute!=null)? attribute.getValue():null;
    }

    public String getDefaultDestroyMethod() throws ParsingException, IOException {
        Document document = parser.build(this.getClass().getClassLoader().getResourceAsStream(this.fileName));
        Element root = document.getRootElement();
        Attribute attribute = root.getAttribute(ParserStringConstants.BEANS_DEFAULT_DESTROY);
        return (attribute!=null)? attribute.getValue():null;
    }


    public Map<String, Bean> getBeans() throws ParsingException, IOException {

        Document document = parser.build(this.getClass().getClassLoader().getResourceAsStream(this.fileName));
        Element root = document.getRootElement();
        Element annotations = root.getFirstChildElement(ParserStringConstants.BEANS_SCAN_ANNOTATIONS);
        if(annotations != null){
            /*Hacer algo para parsear anotaciones*/
            System.out.println("Hay que escanear paquete: " + annotations.getAttribute(ParserStringConstants.BEANS_SCAN_ANNOTATIONS_PACKAGE));
        }
        Elements children = root.getChildElements(ParserStringConstants.BEAN_LABEL);
        Bean newBean;
        for (int i = 0; i < children.size(); i++) {
            newBean = this.createBean(children.get(i));
            this.beansDefinition.put(newBean.getId(),newBean);
        }
        return this.beansDefinition;
    }

    public Bean createBean(Element beanDefinition){
        try{
            Bean newBean = new Bean();
            BeanProperty beanProperty;
            String propertyValue;
            for(int j = 0; j < beanDefinition.getAttributeCount(); j++) {
                beanProperty = BeanProperty.getProperty(beanDefinition.getAttribute(j).getLocalName());
                propertyValue = beanDefinition.getAttribute(j).getValue();
                switch (beanProperty){
                    case ID:
                        //System.out.print("Se vio un id ");
                        newBean.setId(propertyValue);
                        break;
                    case CLASS:
                        //System.out.print("Se vio un class ");
                        newBean.setClassType(propertyValue);
                        break;
                    case INIT:
                        //System.out.print("Se vio un init ");
                        newBean.setInit(propertyValue);
                        break;
                    case DESTROY:
                        //System.out.print("Se vio un destroy ");
                        newBean.setDestroy(propertyValue);
                        break;
                    case SCOPE:
                        //System.out.print("Se vio un scope ");
                        newBean.setScopeType(Scope.valueOf(propertyValue.toUpperCase()));
                        break;
                    case AUTOWIRING:
                        newBean.setAutowireMode(AutowireMode.valueOf(propertyValue.toUpperCase()));
                        break;
                    default:
                        //System.out.println("Error");
                        break;
                }
                //System.out.println(beanDefinition.getAttribute(j).getValue());
            }

            this.getBeanArgs(beanDefinition.getChildElements(), newBean);

            return newBean;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void getBeanArgs(Elements beanArgs, Bean newBean){
        Element argument;
        Bean newNestedBean;
        Parameter newParameter;
        List<Parameter> constructorArgs = new LinkedList<Parameter>();
        List<Parameter> properties = new LinkedList<Parameter>();
        List<Bean> nestedBeans = new LinkedList<Bean>();
        for(int i =0; i < beanArgs.size(); i++){
            argument = beanArgs.get(i);
            switch (BeanArgument.getArgument(argument.getLocalName())){
                case CONSTRUCTOR_ARG:
                    //System.out.println("Varas del constructor ");
                    newParameter = this.getConstructorInformation(argument);
                    constructorArgs.add(newParameter);
                    break;
                case PROPERTY:
                    //System.out.println("Varas de properties ");
                    newParameter = this.getPropertiesInformation(argument);
                    properties.add(newParameter);
                    break;
                case BEAN:
                    //System.out.println("Bean anidado ");
                    newNestedBean = this.createBean(argument);
                    nestedBeans.add(newNestedBean);
                    break;
                case ERROR:
                    //System.out.println("Error!");
                    break;
            }
        }
        newBean.setConstructorArguments(constructorArgs);
        newBean.setProperties(properties);
        newBean.setBeanDependencies(nestedBeans);
    }

    private Parameter getConstructorInformation(Element info){
        Parameter parameter = new Parameter();
        ParameterElement parameterElement;
        String constructorElementValue;
        for (int i = 0; i < info.getAttributeCount(); i++) {
            parameterElement = ParameterElement.valueOf(info.getAttribute(i).getLocalName().toUpperCase());
            constructorElementValue = info.getAttribute(i).getValue();
            switch (parameterElement){
                case REF:
                    parameter.setBeanRef(constructorElementValue);
                    break;
                case NAME:
                    parameter.setName(constructorElementValue);
                    break;
                case INDEX:
                    parameter.setIndex(Integer.parseInt(constructorElementValue));
                    break;
            }
        }
        return parameter;
    }

    private Parameter getPropertiesInformation(Element info){
        Parameter parameter = new Parameter();
        ParameterElement parameterElement;
        String propertyElementValue;
        for (int i = 0; i < info.getAttributeCount(); i++) {
            parameterElement = ParameterElement.valueOf(info.getAttribute(i).getLocalName().toUpperCase());
            propertyElementValue = info.getAttribute(i).getValue();
            switch (parameterElement){
                case REF:
                    parameter.setBeanRef(propertyElementValue);
                    break;
                case NAME:
                    parameter.setName(propertyElementValue);
                    break;
            }
        }
        return parameter;
    }

    public static void main(final String[] args)
    {
        try {
            XmlParser xmlParser = new XmlParser("beans.xml");
            System.out.println(xmlParser.getDefaultInitMethod());
            System.out.println(xmlParser.getDefaultDestroyMethod());
            Map<String,Bean> container = xmlParser.getBeans();
            Bean bean;
            for (Map.Entry<String,Bean> element : container.entrySet()){
                System.out.println(element.getKey());
                bean = element.getValue();
                System.out.println(bean.getAutowireMode());
                System.out.println(bean.getConstructorArguments().size());
                System.out.println(bean.getProperties().size());
                System.out.println(bean.getBeanDependencies().size());
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
