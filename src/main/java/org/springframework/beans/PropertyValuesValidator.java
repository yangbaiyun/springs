package org.springframework.beans;

/**
 * 验证PropertyValues是否正确
 * @author chl
 * @date 2018/12/11 20:10
 */
public interface PropertyValuesValidator {


    void validatePropertyValues(PropertyValues pvs) throws Exception;


}
