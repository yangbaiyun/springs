package org.springframework.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.*;
import java.util.HashMap;

/**
 * 缓存java对象的PropertyDescriptor
 * @author chl
 * @date 2018/12/13 18:13
 */
public class CachedIntrospectionResults {

    private static final Log logger = LogFactory.getLog(CachedIntrospectionResults.class);


    private static HashMap classCache = new HashMap();

    protected static CachedIntrospectionResults forClass(Class clazz) throws Exception {
        Object o = classCache.get(clazz);
        if (o == null) {
            // Can throw BeansException
            o = new CachedIntrospectionResults(clazz);
            classCache.put(clazz, o);
        }
        else {
            if (logger.isDebugEnabled()) {
                logger.debug("Using cached introspection results for class " + clazz.getName());
            }
        }
        return (CachedIntrospectionResults) o;
    }


    private BeanInfo beanInfo;


    private HashMap propertyDescriptorMap;

    private HashMap methodDescriptorMap;


    private CachedIntrospectionResults(Class clazz) throws Exception {
        try {

            this.beanInfo = Introspector.getBeanInfo(clazz);


            this.propertyDescriptorMap = new HashMap();
            // This call is slow so we do it once
            PropertyDescriptor[] pds = this.beanInfo.getPropertyDescriptors();
            for (int i = 0; i < pds.length; i++) {
                logger.debug("Found property '" + pds[i].getName() + "' of type [" + pds[i].getPropertyType() +
                        "]; editor=[" + pds[i].getPropertyEditorClass() + "]");
                this.propertyDescriptorMap.put(pds[i].getName(), pds[i]);
            }

            logger.debug("Caching MethodDescriptors for class [" + clazz.getName() + "]");
            this.methodDescriptorMap = new HashMap();
            // This call is slow so we do it once
            MethodDescriptor[] mds = this.beanInfo.getMethodDescriptors();
            for (int i = 0; i < mds.length; i++) {
                logger.debug("Found method '" + mds[i].getName() + "' of type [" + mds[i].getMethod().getReturnType() + "]");
                this.methodDescriptorMap.put(mds[i].getName(), mds[i]);
            }
        }
        catch (IntrospectionException ex) {

        }
    }



    protected BeanInfo getBeanInfo() {
        return beanInfo;
    }

    protected Class getBeanClass() {
        return beanInfo.getBeanDescriptor().getBeanClass();
    }

    protected PropertyDescriptor getPropertyDescriptor(String propertyName) throws Exception {
        PropertyDescriptor pd = (PropertyDescriptor) this.propertyDescriptorMap.get(propertyName);
        if (pd == null) {
           throw  new RuntimeException();
        }
        return pd;
    }

    protected MethodDescriptor getMethodDescriptor(String methodName) throws Exception {
        MethodDescriptor md = (MethodDescriptor) this.methodDescriptorMap.get(methodName);
        if (md == null) {
            throw  new RuntimeException();
        }
        return md;
    }



}
