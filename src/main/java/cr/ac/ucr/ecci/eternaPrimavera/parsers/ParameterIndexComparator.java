package cr.ac.ucr.ecci.eternaPrimavera.parsers;

import cr.ac.ucr.ecci.eternaPrimavera.annotations.Scope;
import cr.ac.ucr.ecci.eternaPrimavera.bean.Parameter;

import java.util.Comparator;

/**
 * Comparator for the list of parameters.
 * @author Vladimir Aguilar
 */
@Scope
public class ParameterIndexComparator implements Comparator<Parameter>{
    public int compare(Parameter o1, Parameter o2) {
        return o1.getIndex().compareTo(o2.getIndex());
    }
}
