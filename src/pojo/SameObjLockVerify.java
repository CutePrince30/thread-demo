package pojo;

/**
 * Created by sunyunjie on 8/30/16.
 */
public class SameObjLockVerify {

    public static Object staticObj = new Object();

    public void objLock(Object obj) {
        synchronized (obj) {
            track("objLock");
        }
    }

    public void staticObjLock() {
        synchronized (SameObjLockVerify.staticObj) {
            track("staticObjLock");
        }
    }

    /**
     * 循环输出一段内容
     **/
    private static void track(String callerName) {
        for (int i = 0; i < 3; i++)
            System.out.println(
                    callerName + ":" + i + "\t|" +
                            Thread.currentThread().getName());

        Thread.yield();
    }

}
