package TEST;

import java.lang.ref.SoftReference;

/**
 * @author chl
 * @date 2018/12/11 20:59
 */
public class Person {


    public String name;

    public Person(String name)
    {
        this.name=name;
    }

    public static void main(String[] args)
    {
        SoftReference<Person> bean = new SoftReference<Person>(new Person("杨白云"));
        System.out.println(bean.get().name);//
    }


}
