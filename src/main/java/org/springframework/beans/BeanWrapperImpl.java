package org.springframework.beans;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyVetoException;
import java.util.Map;

/**
 * @author chl
 * @date 2018/12/12 19:09
 */
public class BeanWrapperImpl implements BeanWrapper {


    public BeanWrapperImpl(Object object) throws Exception {
        //setWrappedInstance(object);
    }

    @Override
    public void setWrappedInstance(Object obj) throws Exception {

    }

    @Override
    public void newWrappedInstance() throws Exception {

    }

    @Override
    public Object getWrappedInstance() {
        return null;
    }

    @Override
    public Class getWrappedClass() {
        return null;
    }

    @Override
    public void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor) {

    }

    @Override
    public void registerCustomEditor(Class requiredType, String propertyPath, PropertyEditor propertyEditor) {

    }

    @Override
    public PropertyEditor findCustomEditor(Class requiredType, String propertyPath) {
        return null;
    }

    @Override
    public Object getPropertyValue(String propertyName) throws Exception {
        return null;
    }

    @Override
    public void setPropertyValue(String propertyName, Object value) throws PropertyVetoException {

    }

    @Override
    public void setPropertyValue(PropertyValue pv) throws PropertyVetoException {

    }

    @Override
    public void setPropertyValues(Map m) throws Exception {

    }

    @Override
    public void setPropertyValues(PropertyValues pvs) throws Exception {

    }

    @Override
    public void setPropertyValues(PropertyValues pvs, boolean ignoreUnknown, PropertyValuesValidator pvsValidator) throws Exception {

    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() throws Exception {
        return new PropertyDescriptor[0];
    }

    @Override
    public PropertyDescriptor getPropertyDescriptor(String propertyName) throws Exception {
        return null;
    }

    @Override
    public boolean isReadableProperty(String propertyName) {
        return false;
    }

    @Override
    public boolean isWritableProperty(String propertyName) {
        return false;
    }

    @Override
    public Object invoke(String methodName, Object[] args) throws Exception {
        return null;
    }
}
