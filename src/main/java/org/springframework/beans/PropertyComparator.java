package org.springframework.beans;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * @author chl
 * @date 2018/12/12 19:02
 */
public class PropertyComparator  implements Comparator {

    protected final Log logger = LogFactory.getLog(getClass());


    private SortDefinition sortDefinition;

    private Map cachedBeanWrappers = new HashMap();


    public PropertyComparator(SortDefinition sortDefinition) {
        this.sortDefinition = sortDefinition;
    }


    @Override
    public int compare(Object o1, Object o2)  {

        int result;
        try {
            Object v1 = getPropertyValue(o1);
            Object v2 = getPropertyValue(o2);
            if (this.sortDefinition.isIgnoreCase() && (v1 instanceof String) && (v2 instanceof String)) {
                v1 = ((String) v1).toLowerCase();
                v2 = ((String) v2).toLowerCase();
            }


            if(v1!=null)
            {
                result=((Comparable)v1).compareTo(v2);
            }else
            {
                if(v2!=null)
                {
                    result=-((Comparable)v2).compareTo(v1);
                }else
                    return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


        return 0 ;
    }

    private Object getPropertyValue(Object o) throws Exception {
        BeanWrapper bw = (BeanWrapper) this.cachedBeanWrappers.get(o);
        if (bw == null) {
            bw = new BeanWrapperImpl(o);
            this.cachedBeanWrappers.put(o, bw);
        }
        return bw.getPropertyValue(this.sortDefinition.getProperty());
    }
    public static void sort(List source, SortDefinition sortDefinition) throws Exception {
        Collections.sort(source, new PropertyComparator(sortDefinition));
    }

}
