package org.springframework.beans;

import java.util.*;

/**
 * 基本实现类,可以实现深度拷贝实例化或者从Map中实例化
 * @author chl
 * @date 2018/12/11 19:37
 */
public class MutablePropertyValues implements PropertyValues {

    /** 保存 PropertyValues **/
    private List propertyValuesList;



    public MutablePropertyValues(){ propertyValuesList = new ArrayList(10);}

    public MutablePropertyValues(PropertyValues other)
    {

        if(other!=null)
        {
            PropertyValue[] pvs=other.getPropertyValues();
            this.propertyValuesList=new ArrayList(pvs.length);
            for(int i=0;i<pvs.length;i++)
            {
                addPropertyValue(new PropertyValue(pvs[i].getName(),pvs[i].getValue()));
            }
        }
    }

    public MutablePropertyValues(Map map)
    {
        Set keys=map.keySet();
        this.propertyValuesList=new ArrayList(keys.size());

        Iterator iterator=keys.iterator();
        while(iterator.hasNext())
        {
            String key=(String)iterator.next();
            addPropertyValue(new PropertyValue(key,map.get(key)));
        }
    }









    /**
     * 添加PropertyValue 如果已经存在则覆盖
     * @param pv
     */
    public void addPropertyValue(PropertyValue pv)
    {
        for(int i=0;i<propertyValuesList.size();i++)
        {
            PropertyValue currentPv=(PropertyValue)propertyValuesList.get(i);
            if(currentPv.getName().equals(pv.getName()))
            {
                propertyValuesList.set(i,pv);
                return;
            }
        }
        propertyValuesList.add(pv);
    }

    public void addPropertyValue(String propertyName,Object propertyValue)
    {
        this.addPropertyValue(new PropertyValue(propertyName,propertyValue));
    }

    @Override
    public PropertyValue[] getPropertyValues() {
        return (PropertyValue[])this.propertyValuesList.toArray(new PropertyValue[0]);
    }

    @Override
    public PropertyValue getPropertyValue(String propertyName) {

        for(int i=0;i<propertyValuesList.size();i++)
        {
            PropertyValue pv=(PropertyValue)propertyValuesList.get(i);
            if(pv.getName().equals(propertyName))
            {
                return pv;
            }
        }
        return null;
    }

    @Override
    public boolean contains(String propertyName) {

       return getPropertyValue(propertyName)!=null;

    }

    @Override
    public PropertyValues changesSince(PropertyValues old) {




        return null;
    }
}
