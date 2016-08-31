import org.junit.Test;
import pojo.SynchronizedVerify;

public class SynchronizedVerifyTest {

    /*synchronized修饰代码块*/

    /**
     * sv.blockStyle() 以私有成员ObjA为对象锁
     * sv.blockNormal() 非同步方法且不含同步代码块
     * 无竞争
     */
    @Test
    public void test1() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.blockStyle(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { sv.blockNormal(); };
        }.start();
    }

    /**
     * sv.blockStyle() 以私有成员ObjA为对象锁
     * sv.blockContrast() 以私有成员ObjA为对象锁
     * 有竞争
     */
    @Test
    public void test2() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.blockStyle(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { sv.blockContrast(); };
        }.start();
    }

    /**
     * sv.blockStyle() 以私有成员ObjA为对象锁
     * sv.blockDiffObj() 以私有成员ObjB为对象锁
     * 无竞争
     */
    @Test
    public void test3() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.blockStyle(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { sv.blockDiffObj(); };
        }.start();
    }

    /**
     * sv.blockStyle() 以私有成员ObjA为对象锁
     * sv.methodStyle() 同步成员方法
     * 无竞争
     */
    @Test
    public void test4() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.blockStyle(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { sv.methodStyle(); };
        }.start();
    }

    /**
     * sv.methodStyle() 同步成员方法
     * sv.blockOneself() 以this为对象锁
     * 有竞争
     */
    @Test
    public void test5() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.methodStyle(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { sv.blockOneself(); };
        }.start();
    }

    /**
     * 因此当我们以类A为类锁锁定类A，以类A对象a为对象锁锁定对象a时，
     * 两个线程分别访问类A锁定的代码块、对象a锁定的代码块时，并不会产生竞争。
     *
     * sv.blockOneself() 以this为对象锁
     * sv.blockClass() 以类为类锁
     */
    @Test
    public void test6() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.blockOneself(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { sv.blockClass();  };
        }.start();
    }

    /**
     * 线程进入同步静态方法，需要申请的是该类对应的Class类对象的内置锁。
     * 这使得synchronized(A.class){}代码块 与 A类的同步静态方法前后被多线程访问时，
     * 需要申请同一个锁，于是便产生了竞争
     *
     * sv.blockClass() 以类为类锁
     * SynchronizedVerify.methodStatic() 类的同步静态方法,以类为类锁
     */
    @Test
    public void test7() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.blockClass(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { SynchronizedVerify.methodStatic(); };
        }.start();
    }

    /*synchronized修饰方法*/

    /**
     * sv.methodStyle() 类的同步方法,sv为锁
     * sv.methodNormal() 类的非同步方法,无锁
     * 无竞争
     */
    @Test
    public void test8() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.methodStyle(); };
        }.start();

        new Thread(){//定为线程二
            public void run() {  sv.methodNormal(); };
        }.start();
    }

    /**
     * sv.methodStyle() 类的同步方法,sv为锁
     * sv.methodContrast() 类的同步方法,sv为锁
     * 有竞争
     */
    @Test
    public void test9() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.methodStyle(); };
        }.start();

        new Thread(){//定为线程二
            public void run() {  sv.methodContrast(); };
        }.start();
    }

    /**
     * sv.methodStyle() 类的同步方法,sv为锁
     * sv.blockDiffObj() objB为锁
     * 无竞争
     */
    @Test
    public void test10() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.methodStyle(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { sv.blockDiffObj(); };
        }.start();
    }

    /**
     * sv.methodStyle() 类的同步方法,sv为锁
     * sv.blockOneself() 类的同步代码,调用方sv为锁
     * 有竞争
     */
    @Test
    public void test11() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { sv.methodStyle(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { sv.blockOneself(); };
        }.start();
    }

    /**
     * 线程一与线程二无竞争
     * 线程一与线程三有竞争
     */
    @Test
    public void test12() {
        new Thread(){//定为线程一
            public void run() { SynchronizedVerify.methodStatic(); };
        }.start();

        new Thread(){//定为线程二
            public void run() {  SynchronizedVerify.methodStaticNormal(); };
        }.start();

        new Thread(){//定为线程三
            public void run() {  SynchronizedVerify.methodStaticContrast(); };
        }.start();
    }

    /**
     * 线程一与线程二无竞争
     * 线程一与线程三有竞争
     */
    @Test
    public void test13() {
        final SynchronizedVerify sv = new SynchronizedVerify();

        new Thread(){//定为线程一
            public void run() { SynchronizedVerify.methodStatic(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { sv.blockOneself(); };
        }.start();

        new Thread(){//定为线程三
            public void run() { sv.blockClass(); };
        }.start();
    }

}
