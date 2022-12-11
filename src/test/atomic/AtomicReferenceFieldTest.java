package test.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author Chenkunwei
 * @version 1.0.0
 * @ClassName testAtomicReferenceField.java
 * @Description TODO
 * @createTime 2021年09月24日 20:14:00
 */
public class AtomicReferenceFieldTest {

    public static void main(String[] args) {
        testAtomicReferenceField();
    }

    private static void testAtomicReferenceField() {
        AtomicReferenceFieldUpdater<User, String> updateName = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class,"name");
        AtomicIntegerFieldUpdater<User> updateAge = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

        User user = new User("tong ge", 21);
        updateName.compareAndSet(user, "tong ge", "read source code");
        updateAge.compareAndSet(user, 21, 25);
        updateAge.incrementAndGet(user);

        System.out.println(user);
    }

    private static class User {
        volatile String name;
        volatile int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "name: " + name + ", age: " + age;
        }
    }
}


