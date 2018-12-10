package org.springframework.beans;

/**
 * @author chl
 * @date 2018/12/10 21:04
 */
public class PropertyValue {

   /** 属性名称 **/
   private String name;
    /** 属性值 **/
   private Object value;


   public PropertyValue(String name,Object value)
   {
         this.name=name;
         this.value=value;
   }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String toString()
    {
        return "PropertyValue: name='"+name+"',value=["+value+"]";
    }

    public boolean equals(Object other)
    {
        if(!(other instanceof PropertyValue))
            return false;

        PropertyValue pvOther=(PropertyValue)other;

        return other==this || (this.name==pvOther.name && this.value==pvOther.value);


    }


}
