package org.springframework.beans;

import org.springframework.beans.propertyeditors.PropertiesEditor;

import java.beans.PropertyEditorSupport;
import java.util.Properties;

/**
 * @author chl
 * @date 2018/12/12 20:58
 */
public class PropertyValuesEditor  extends PropertyEditorSupport {


    public void setAsText(String s) throws IllegalArgumentException {
        PropertiesEditor pe = new PropertiesEditor();
        pe.setAsText(s);
        Properties props = (Properties) pe.getValue();
        setValue(new MutablePropertyValues(props));
    }



}
