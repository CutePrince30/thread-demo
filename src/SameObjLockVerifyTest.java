import org.junit.Test;
import pojo.SameObjLockVerify;

/**
 * Created by sunyunjie on 8/30/16.
 */
public class SameObjLockVerifyTest {

    /**
     * 同一个对象objLock作对象锁时，不同线程进入不同对象solv1、solv2时有竞争
     */
    @Test
    public void test1() {
        final SameObjLockVerify solv1 = new SameObjLockVerify();
        final SameObjLockVerify solv2 = new SameObjLockVerify();
        final Object objLock = new Object();

        new Thread(){//定为线程一
            public void run() { solv1.objLock(objLock); };
        }.start();

        new Thread(){//定为线程二
            public void run() { solv2.objLock(objLock); };
        }.start();
    }

    /**
     * 即使不是同一个类对象，只要synchronized(对象锁){}使用了相同的对象锁，就会造成多线程的资源竞争
     */
    @Test
    public void test2() {
        final SameObjLockVerify solv1 = new SameObjLockVerify();
        final SameObjLockVerify solv2 = new SameObjLockVerify();

        new Thread(){//定为线程一
            public void run() { solv1.staticObjLock(); };
        }.start();

        new Thread(){//定为线程二
            public void run() { solv2.staticObjLock(); };
        }.start();
    }

}
