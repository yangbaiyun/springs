package org.springframework.beans;

import java.io.Serializable;

/**
 * 排序属性实现类
 * @author chl
 * @date 2018/12/11 20:32
 */
public class MutableSortDefinition implements SortDefinition,Serializable {

    private String property;

    private boolean ignoreCase = true;

    private boolean ascending = true;

    private boolean toggleAscendingOnProperty = false;

    public MutableSortDefinition() {
    }

    public MutableSortDefinition(SortDefinition source) {
        this.property = source.getProperty();
        this.ignoreCase = source.isIgnoreCase();
        this.ascending = source.isAscending();
    }

    public MutableSortDefinition(String property, boolean ignoreCase, boolean ascending) {
        this.property = property;
        this.ignoreCase = ignoreCase;
        this.ascending = ascending;
    }

    public MutableSortDefinition(boolean toggleAscendingOnSameProperty) {
        this.toggleAscendingOnProperty = toggleAscendingOnSameProperty;
    }


    public void setProperty(String property) {
        if (property == null || "".equals(property)) {
            this.property = "";
        }
        else {
            // implicit toggling of ascending?
            if (this.toggleAscendingOnProperty) {
                if (property.equals(this.property)) {
                    ascending = !ascending;
                }
                else {
                    this.ascending = true;
                }
            }
            this.property = property;
        }
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public boolean isToggleAscendingOnProperty() {
        return toggleAscendingOnProperty;
    }

    public void setToggleAscendingOnProperty(boolean toggleAscendingOnProperty) {
        this.toggleAscendingOnProperty = toggleAscendingOnProperty;
    }

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public boolean isIgnoreCase() {
        return false;
    }

    @Override
    public boolean isAscending() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SortDefinition)) {
            return false;
        }
        SortDefinition sd = (SortDefinition) obj;
        return (getProperty().equals(sd.getProperty()) &&
                isAscending() == sd.isAscending() && isIgnoreCase() == sd.isIgnoreCase());
    }

    @Override
    public int hashCode() {
        int result = property != null ? property.hashCode() : 0;
        result = 31 * result + (ignoreCase ? 1 : 0);
        result = 31 * result + (ascending ? 1 : 0);
        result = 31 * result + (toggleAscendingOnProperty ? 1 : 0);
        return result;
    }
}
