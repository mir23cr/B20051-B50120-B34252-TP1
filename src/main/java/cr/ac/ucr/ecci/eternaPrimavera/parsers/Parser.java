package cr.ac.ucr.ecci.eternaPrimavera.parsers;

import cr.ac.ucr.ecci.eternaPrimavera.bean.Bean;
import java.util.Map;

/**
 * Interface that defines the Parser functions
 * @author Rodrigo Acuña
 * @author Vladimir Aguilar
 * @author José Mesén
 */
public interface Parser {

    /**
     * Get all the beans for the cr.ac.ucr.ecci.eternaPrimavera.annotations or the XML file
     * @return a map with the container.
     */
    Map<String, Bean> getBeans();


}
