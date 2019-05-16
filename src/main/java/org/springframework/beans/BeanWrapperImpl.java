package org.springframework.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.exceptions.BeansException;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.PropertyVetoException;
import java.lang.reflect.Method;
import java.util.*;

public class BeanWrapperImpl implements BeanWrapper {
    private static final Log logger = LogFactory.getLog(BeanWrapperImpl.class);

    private static final Map defaultEditors = new HashMap();


    static{

        try {
            PropertyEditorManager.registerEditor(String[].class, StringArrayPropertyEditor.class);
            PropertyEditorManager.setEditorSearchPath(new String[]{"com.sun.beans.editors",
                    "org.springframework.beans.propertyeditors"});
        } catch (Exception e) {
            logger.warn("Cannot register property editors with PropertyEditorManager", e);
        }

        defaultEditors.put(Properties.class, new PropertiesEditor());
        defaultEditors.put(String[].class, new StringArrayPropertyEditor());



    }


    /** The wrapped object */
    private Object object;

    /** The nested path of the object */
    private String nestedPath = "";

    /* Map with cached nested BeanWrappers */
    private Map nestedBeanWrappers;

    /** Map with custom PropertyEditor instances */
    private Map customEditors;

    /**
     * Cached introspections results for this object, to prevent encountering the cost
     * of JavaBeans introspection every time.
     */
    private CachedIntrospectionResults cachedIntrospectionResults;



    public BeanWrapperImpl() {
    }

    public BeanWrapperImpl(Object object) throws BeansException {
        setWrappedInstance(object);
    }


    public BeanWrapperImpl(Object object, String nestedPath) throws BeansException {
        setWrappedInstance(object);
        this.nestedPath = nestedPath;
    }

    /**
     * Create new BeanWrapperImpl, wrapping a new instance of the specified class.
     * @param clazz class to instantiate and wrap
     * @throws BeansException if the class cannot be wrapped by a BeanWrapper
     */
    public BeanWrapperImpl(Class clazz) throws BeansException {
        setWrappedInstance(BeanUtils.instantiateClass(clazz));
    }




    public void setWrappedInstance(Object obj) throws BeansException {
        if (object == null)
            throw new RuntimeException("Cannot set BeanWrapperImpl target to a null object", null);
        this.object = object;
        if (this.cachedIntrospectionResults == null ||
                !this.cachedIntrospectionResults.getBeanClass().equals(object.getClass())) {
            this.cachedIntrospectionResults = CachedIntrospectionResults.forClass(object.getClass());
        }
    }


    public void newWrappedInstance() throws Exception {
        this.object = BeanUtils.instantiateClass(getWrappedClass());
    }


    public Object getWrappedInstance() {
        return object;
    }


    public Class getWrappedClass() {
        return object.getClass();
    }


    public void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor) {
        registerCustomEditor(requiredType, null, propertyEditor);
    }


    public void registerCustomEditor(Class requiredType, String propertyPath, PropertyEditor propertyEditor) {

    }


    public PropertyEditor findCustomEditor(Class requiredType, String propertyPath) {
        return null;
    }

    @Override
    public Object getPropertyValue(String propertyName) throws Exception {
        return null;
    }

    @Override
    public void setPropertyValue(String propertyName, Object value) throws PropertyVetoException {
        setPropertyValue(new PropertyValue(propertyName, value));
    }

    @Override
    public void setPropertyValue(PropertyValue pv) throws PropertyVetoException {

        if (isNestedProperty(pv.getName())) {
            try {
                BeanWrapper nestedBw = getBeanWrapperForPropertyPath(pv.getName());
                nestedBw.setPropertyValue(new PropertyValue(getFinalPath(pv.getName()), pv.getValue()));
                return;

            }
            catch (Exception ex) {
                throw ex;
            }
        }






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
    public PropertyDescriptor[] getPropertyDescriptors() throws BeansException {
        return new PropertyDescriptor[0];
    }

    @Override
    public PropertyDescriptor getPropertyDescriptor(String propertyName) throws BeansException {

        if (propertyName == null) {
            throw new BeansException("Can't find property descriptor for null property");
        }
        if (isNestedProperty(propertyName)) {
            BeanWrapper nestedBw = getBeanWrapperForPropertyPath(propertyName);
            return nestedBw.getPropertyDescriptor(getFinalPath(propertyName));
        }
        return this.cachedIntrospectionResults.getPropertyDescriptor(propertyName);
    }

    @Override
    public boolean isReadableProperty(String propertyName) {
        return false;
    }

    @Override
    public boolean isWritableProperty(String propertyName)  {

        if(propertyName==null)
            throw new RuntimeException("属性不存在");
        try
        {
            return getPropertyDescriptor(propertyName).getWriteMethod()!=null;
        }catch (Exception e)
        {
            return false;
        }

    }

    @Override
    public Object invoke(String methodName, Object[] args) throws BeansException {
        return null;
    }
    /** 判断是否是嵌入式属性值，就是给出的属性名中有没有.分隔符**/
    private boolean isNestedProperty(String path) {
        return path.indexOf(NESTED_PROPERTY_SEPARATOR) != -1;
    }

    //获取属性路径中的最后属性名  perosn.name  获取name
    private String getFinalPath(String nestedPath) {
        String finalPath = nestedPath.substring(nestedPath.lastIndexOf(NESTED_PROPERTY_SEPARATOR) + 1);
        if (logger.isDebugEnabled() && !nestedPath.equals(finalPath)) {
            logger.debug("Final path in nested property value '" + nestedPath + "' is '" + finalPath + "'");
        }
        return finalPath;
    }

    private BeanWrapperImpl getBeanWrapperForPropertyPath(String propertyPath) {

        int pos=propertyPath.indexOf(NESTED_PROPERTY_SEPARATOR);
        if(pos!=-1)
        {
            String nestedProperty=propertyPath.substring(0,pos);
            String nestedPath=propertyPath.substring(pos+1);
            BeanWrapperImpl nestedBw = getNestedBeanWrapper(nestedProperty);
            return nestedBw.getBeanWrapperForPropertyPath(nestedPath);

        }
        else
            return this;

    }


    //获取嵌入式属性的beanwrapper,如果缓存中找不到则创建一个
    private BeanWrapperImpl getNestedBeanWrapper(String nestedProperty) {

        if (this.nestedBeanWrappers == null) {
            this.nestedBeanWrappers = new HashMap();
        }
        // get value of bean property
        String[] tokens = getPropertyNameTokens(nestedProperty);
        Object propertyValue = getPropertyValue(tokens[0], tokens[1], tokens[2]);
        String canonicalName = tokens[0];
        if (propertyValue == null) {
            throw new BeansException(nestedProperty+":propertyValue == null");
        }
// lookup cached sub-BeanWrapper, create new one if not found
        BeanWrapperImpl nestedBw = (BeanWrapperImpl) this.nestedBeanWrappers.get(canonicalName);
        if (nestedBw == null) {
            logger.debug("Creating new nested BeanWrapper for property '" + canonicalName + "'");
            nestedBw = new BeanWrapperImpl(propertyValue, this.nestedPath + canonicalName + NESTED_PROPERTY_SEPARATOR);
            // inherit all type-specific PropertyEditors
            if (this.customEditors != null) {
                for (Iterator it = this.customEditors.keySet().iterator(); it.hasNext();) {
                    Object key = it.next();
                    if (key instanceof Class) {
                        Class requiredType = (Class) key;
                        PropertyEditor propertyEditor = (PropertyEditor) this.customEditors.get(key);
                        nestedBw.registerCustomEditor(requiredType, null, propertyEditor);
                    }
                }
            }
            this.nestedBeanWrappers.put(canonicalName, nestedBw);
        }
        else {
            logger.debug("Using cached nested BeanWrapper for property '" + canonicalName + "'");
        }
        return nestedBw;
    }


    private String[] getPropertyNameTokens(String propertyName) {
        String actualName = propertyName;
        String key = null;
        int keyStart = propertyName.indexOf('[');


        if (keyStart != -1 && propertyName.endsWith("]")) {
            actualName = propertyName.substring(0, keyStart);
            key = propertyName.substring(keyStart + 1, propertyName.length() - 1);
            if (key.startsWith("'") && key.endsWith("'")) {
                key = key.substring(1, key.length() - 1);
            }
            else if (key.startsWith("\"") && key.endsWith("\"")) {
                key = key.substring(1, key.length() - 1);
            }
        }

        String canonicalName = actualName;
        if (key != null) {
            canonicalName += "[" + key + "]";
        }
        return new String[] {canonicalName, actualName, key};
    }

    private Object getPropertyValue(String propertyName, String actualName, String key) {
        PropertyDescriptor pd = getPropertyDescriptor(actualName);
        Method readMethod = pd.getReadMethod();
        if (readMethod == null) {
            throw new BeansException("Cannot get property '" + actualName + "': not readable", null);
        }
        if (logger.isDebugEnabled())
            logger.debug("About to invoke read method [" + readMethod +
                    "] on object of class [" + this.object.getClass().getName() + "]");
        try {
            Object value = readMethod.invoke(this.object, null);
            if (key != null) {
                if (value == null) {
                    throw new BeansException("Cannot access indexed value in property referenced in indexed property path '" +
                            propertyName + "': returned null");
                }
                else if (value.getClass().isArray()) {
                    Object[] array = (Object[]) value;
                    return array[Integer.parseInt(key)];
                }
                else if (value instanceof List) {
                    List list = (List) value;
                    return list.get(Integer.parseInt(key));
                }
                else if (value instanceof Map) {
                    Map map = (Map) value;
                    return map.get(key);
                }
                else {
                    throw new BeansException("Property referenced in indexed property path '" + propertyName +
                            "' is neither an array nor a List nor a Map; returned value was [" + value + "]");
                }
            }
            else {
                return value;
            }
        }
        catch (Exception ex) {
            throw new BeansException(ex.getMessage(), ex);
        }

    }


}
