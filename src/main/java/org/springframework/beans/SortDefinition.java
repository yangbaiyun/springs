package org.springframework.beans;

/**
 * 根据某个bean属性排序
 * @author chl
 * @date 2018/12/11 20:19
 */
public interface SortDefinition {


    String getProperty();

    boolean isIgnoreCase();


    boolean isAscending();


}
