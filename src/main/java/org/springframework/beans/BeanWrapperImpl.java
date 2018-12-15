package org.springframework.beans;

import com.sun.beans.finder.PropertyEditorFinder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author chl
 * @date 2018/12/12 19:09
 */
public class BeanWrapperImpl implements BeanWrapper {


    private static final Log logger = LogFactory.getLog(BeanWrapperImpl.class);

    private static final Map defaultEditors = new HashMap();


    static{

        try {
            PropertyEditorManager.registerEditor(String[].class,StringArrayPropertyEditor.class);
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


    public BeanWrapperImpl(Object object) throws Exception {
        setWrappedInstance(object);
    }

    public BeanWrapperImpl(Class clazz) throws Exception {
        setWrappedInstance(BeanUtils.instantiateClass(clazz));
    }


    @Override
    public void setWrappedInstance(Object obj) throws Exception {
        if (object == null)
            throw new RuntimeException("Cannot set BeanWrapperImpl target to a null object", null);
        this.object = object;
        if (this.cachedIntrospectionResults == null ||
                !this.cachedIntrospectionResults.getBeanClass().equals(object.getClass())) {
            this.cachedIntrospectionResults = CachedIntrospectionResults.forClass(object.getClass());
        }
    }

    @Override
    public void newWrappedInstance() throws Exception {
        this.object = BeanUtils.instantiateClass(getWrappedClass());
    }

    @Override
    public Object getWrappedInstance() {
        return object;
    }

    @Override
    public Class getWrappedClass() {
        return object.getClass();
    }

    @Override
    public void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor) {
        registerCustomEditor(requiredType, null, propertyEditor);
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
    public PropertyDescriptor[] getPropertyDescriptors() throws Exception {
        return new PropertyDescriptor[0];
    }

    @Override
    public PropertyDescriptor getPropertyDescriptor(String propertyName) throws Exception {

        if(propertyName==null)
            throw new RuntimeException("属性不存在");

        if(isNestedProperty(propertyName))
        {

        }

        return null;
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
    public Object invoke(String methodName, Object[] args) throws Exception {
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
           // BeanWrapperImpl bw=

        }
        else
            return this;

        return null;
    }


}
