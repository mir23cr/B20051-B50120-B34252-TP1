package parsers;

import bean.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface that defines the Parser functions
 * @author Rodrigo Acuña
 * @author Vladimir Aguilar
 * @author José Mesén
 */
public interface Parser {

    /**
     * Get all the beans for the annotations or the XML file
     * @return a map with the container.
     */
    Map<String, Bean> getBeans();


}
