package org.springframework.beans;

import org.junit.Test;

/**
 * @author chl
 * @date 2018/12/10 21:20
 */
public class PropertyValueTest {



    @Test
    public void TestEquals()
    {
        PropertyValue pv=new PropertyValue("123","");

        System.out.println(pv.equals(null));

    }


}
