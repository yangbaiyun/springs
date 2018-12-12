package org.springframework.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chl
 * @date 2018/12/10 21:20
 */
public class PropertyValueTest {

     private final Log logger= LogFactory.getLog(PropertyValueTest.class);

    @Test
    public void TestEquals()
    {
        PropertyValue pv=new PropertyValue("123","");

        System.out.println(pv.equals(null));

    }

    @Test
    public void TestComparable()
    {
        Person person1=new Person();
        person1.setPrice(500);
        Person person2=new Person();
        person2.setPrice(200);

        List list=new ArrayList();
        list.add(person1);
        list.add(person2);
        logger.info(list.toString());
        Collections.sort(list,new Person());
        logger.info(list.toString());
    }


}
