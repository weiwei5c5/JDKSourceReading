package test.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Chenkunwei
 * @version 1.0.0
 * @ClassName UnsafeTest.java
 * @Description TODO
 * @createTime 2021年09月26日 15:28:00
 */
public class UnsafeTest {
    public static void main(String[] args) throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        //allocateInstance只会给对象分配内存，并不会调用构造方法，所以这里只会返回int类型的默认值0。
//        User user = (User) unsafe.allocateInstance(User.class);
//        System.out.println(user.age);

        User user = new User();
        Field age = user.getClass().getDeclaredField("age");
        unsafe.putInt(user,unsafe.objectFieldOffset(age),20);

        System.out.println(user.getAge());
    }
}

class User{
    int age;

    public User(){
        this.age = 10;
    }

    int getAge(){
        return age;
    }
}
