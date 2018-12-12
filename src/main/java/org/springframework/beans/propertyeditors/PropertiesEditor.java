package org.springframework.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * 编辑java.util.Properties
 * @author chl
 * @date 2018/12/12 20:39
 */
public class PropertiesEditor extends PropertyEditorSupport {


    private final static String COMMENT_MARKERS = "#!";


    public void setAsText(String s) throws IllegalArgumentException {
        if (s == null) {
            throw new IllegalArgumentException("Cannot set Properties to null");
        }
        Properties props = new Properties();
        try {
            props.load(new ByteArrayInputStream(s.getBytes()));
            dropComments(props);
        }
        catch (IOException ex) {
            // Shouldn't happen
            throw new IllegalArgumentException("Failed to read String");
        }
        setValue(props);
    }

    /**
     * 去掉key以#开头的注释
     * @param props
     */
    private void dropComments(Properties props) {
        Iterator keys = props.keySet().iterator();
        List commentKeys = new LinkedList();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            // A comment line starts with one of our comment markers
            if (key.length() > 0 && COMMENT_MARKERS.indexOf(key.charAt(0)) != -1) {
                // We can't actually remove it as we'll get a
                // concurrent modification exception with the iterator
                commentKeys.add(key);
            }
        }
        for (int i = 0; i < commentKeys.size(); i++) {
            String key = (String) commentKeys.get(i);
            props.remove(key);
        }
    }

}
