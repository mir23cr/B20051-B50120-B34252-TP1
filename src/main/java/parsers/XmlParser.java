package parsers;


import java.io.File;
import java.io.IOException;

import nu.xom.*;

/**
 * @author Rodrigo Acuña
 * @author Vladimir Aguilar
 * @author José Mesén
 * Creation Date: 9/9/2017
 */
public class XmlParser implements Parser {

    Builder parser;

    public XmlParser(){
        this.parser = new Builder();
    }

    public void getBeans(String filePath) throws ParsingException, IOException {
        File currentDirectory = new File(new File(".").getAbsolutePath());
        filePath = currentDirectory.getCanonicalPath()+"\\src\\" + filePath;
        Document document = parser.build(this.getClass().getClassLoader().getResourceAsStream(filePath));
        Element root = document.getRootElement();
        Elements children = root.getChildElements(ParserStringConstants.BEAN_LABEL);
        for (int i = 0; i < children.size(); i++) {
            System.out.println(children.get(i));
        }
    }

    public static void main(final String[] args)
    {
        try {
            XmlParser xmlParser = new XmlParser();
            xmlParser.getBeans("main\\resources\\beans.xml");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
