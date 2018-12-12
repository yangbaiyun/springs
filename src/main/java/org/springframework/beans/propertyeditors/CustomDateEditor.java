package org.springframework.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * @author chl
 * @date 2018/12/12 20:31
 */
public class CustomDateEditor  extends PropertyEditorSupport {

    private final DateFormat dateFormat;

    private final boolean allowEmpty;

    public CustomDateEditor(DateFormat dateFormat, boolean allowEmpty) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && text.trim().equals("")) {
            // treat empty String as null value
            setValue(null);
        }
        else {
            try {
                setValue(this.dateFormat.parse(text));
            }
            catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage());
            }
        }
    }

    public String getAsText() {
        return this.dateFormat.format((Date) getValue());
    }


}
