package org.springframework.beans.propertyeditors;

import org.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

/**
 * @author chl
 * @date 2018/12/13 19:15
 */
public class StringArrayPropertyEditor extends PropertyEditorSupport {

    public void setAsText(String s) throws IllegalArgumentException {
        String[] sa = StringUtils.commaDelimitedListToStringArray(s);
        setValue(sa);
    }

    public String getAsText() {
        String[] array = (String[]) this.getValue();
        return StringUtils.arrayToCommaDelimitedString(array);
    }

}
