package org.springframework.beans;

import org.springframework.exceptions.BeansException;

/**
 * @author chl
 * @date 2018/12/13 20:10
 */
public abstract class BeanUtils {



    public static Object instantiateClass(Class clazz) throws BeansException {
        try {
            return clazz.newInstance();
        }
        catch (InstantiationException ex) {
            throw new RuntimeException("Could not instantiate class [" + clazz.getName() +
                    "]; Is it an interface or an abstract class? Does it have a no-arg constructor?", ex);
        }
        catch (IllegalAccessException ex) {
            throw new RuntimeException("Could not instantiate class [" + clazz.getName() +
                    "]; has class definition changed? Is there a public no-arg constructor?", ex);
        }
    }



}
