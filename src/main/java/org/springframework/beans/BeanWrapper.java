package org.springframework.beans;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyVetoException;
import java.util.Map;

/**
 * @author chl
 * @date 2018/12/11 20:41
 */
public interface BeanWrapper {

    //属性分隔符 例如：foo.bar
    String NESTED_PROPERTY_SEPARATOR = ".";

   //obj被封装的对象
    void setWrappedInstance(Object obj) throws Exception;

    void newWrappedInstance() throws Exception;

    //返回被封装的对象
    Object getWrappedInstance();

    //返回被封装的类类型
    Class getWrappedClass();

    //为指定类型注册自定义属性编辑器
    void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor);

    //为指定类型或者属性（属性为空表示所有属性）注册自定义属性编辑器
    void registerCustomEditor(Class requiredType, String propertyPath, PropertyEditor propertyEditor);

    //为指定类型或者属性查找对应的属性编辑器
    PropertyEditor findCustomEditor(Class requiredType, String propertyPath);

    //获取属性值
    Object getPropertyValue(String propertyName) throws Exception;

    //设置置顶属性值
    void setPropertyValue(String propertyName, Object value) throws PropertyVetoException;

    //更新属性值
    void setPropertyValue(PropertyValue pv) throws PropertyVetoException;

    //批量更新属性值，map中包含属性的key,value，内部使用setPropertyValues(PropertyValues pvs)，是个扩展方法
    void setPropertyValues(Map m) throws Exception;

    //推荐的批量更新属性方法
    void setPropertyValues(PropertyValues pvs) throws Exception;

    //是否忽略没找到的属性值
    void setPropertyValues(PropertyValues pvs, boolean ignoreUnknown, PropertyValuesValidator pvsValidator)
            throws Exception;

    PropertyDescriptor[] getPropertyDescriptors() throws Exception;

    PropertyDescriptor getPropertyDescriptor(String propertyName) throws Exception;


    boolean isReadableProperty(String propertyName);

    boolean isWritableProperty(String propertyName);

    //通过名称调用方法，知道就行，不推荐大量使用
    Object invoke(String methodName, Object[] args) throws Exception;

}
