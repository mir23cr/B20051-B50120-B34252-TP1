package parsers;

import annotations.Scope;
import bean.Parameter;

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
