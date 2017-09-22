package parsers;

import annotations.Scope;
import bean.Parameter;

import java.util.Comparator;

/**
 * @author Vladimir Aguilar
 * Creation Date: 18/9/2017
 */
@Scope
public class ParameterIndexComparator implements Comparator<Parameter>{
    public int compare(Parameter o1, Parameter o2) {
        return o1.getIndex().compareTo(o2.getIndex());
    }
}
