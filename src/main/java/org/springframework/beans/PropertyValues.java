package org.springframework.beans;

/**
 * 保存多个PropertyValue,基本方法抽象出来
 * @author chl
 * @date 2018/12/11 19:30
 */
public interface PropertyValues {

    /**获取所有的**/
    PropertyValue[] getPropertyValues();


    /**查找**/
    PropertyValue getPropertyValue(String propertyName);

    /**是否存在某个属性**/
    boolean contains(String propertyName);

    /**返回改变的propertyValue**/
    PropertyValues changesSince(PropertyValues old);

}
