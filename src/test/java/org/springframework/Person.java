package org.springframework;

import java.util.Comparator;

/**
 * @author chl
 * @date 2018/12/12 19:27
 */
public class Person implements Comparator {

    Integer price;
    Integer age;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public int compare(Object o1, Object o2) {


        Integer p1=((Person)o1).price;
        Integer p2=((Person)o2).price;
        int result;
        try {
            if(p1!=null)
            {
                result=((Comparable)p1).compareTo(p2);
            }else
            {
                if(p2!=null)
                {
                    result=-((Comparable)p2).compareTo(p1);
                }else
                {
                    return 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return result;
    }


    public String toString()
    {
        return "price:"+price;
    }
}
