package pojo;

/**
 * Created by sunyunjie on 8/30/16.
 */
public class SynchronizedVerify {

    /**
     * -------------------------------------包裹代码块------------------------------
     **/
    Object objA = new Object();
    Object objB = new Object();

    public void blockNormal() {
        track("blockNormal");
    }

    /**
     * objA is lock
     */
    public void blockStyle() {
        synchronized (objA) {
            track("blockStyle");
        }
    }

    /**
     * objA is lock
     * 相同场景下对比
     **/
    public void blockContrast() {
        synchronized (objA) {
            track("blockContrast");
        }
    }

    /**
     * objB is lock
     */
    public void blockDiffObj() {
        synchronized (objB) {
            track("blockDiffObj");
        }
    }

    /**
     * 调用者 is lock
     */
    public void blockOneself() {
        synchronized (this) {
            track("blockOneself");
        }
    }

    /**
     * SynchronizedVerify is lock
     */
    public void blockClass() {
        synchronized (SynchronizedVerify.class) {
            track("blockClass");
        }
    }

    /**
     * -------------------------------------修饰方法------------------------------
     **/

    /**
     * no lock
     */
    public void methodNormal() {
        track("methodNormal");
    }

    /**
     * 调用者 is lock
     */
    public synchronized void methodStyle() {
        track("methodStyle");
    }

    /**
     * 调用者 is lock
     */
    public synchronized void methodContrast() {
        track("methodContrast");
    }

    /**
     * 相同场景下对比
     **/

    /**
     * no lock
     */
    public static void methodStaticNormal() {
        track("methodStaticNormal");
    }

    /**
     * SynchronizedVerify is lock
     */
    public synchronized static void methodStatic() {
        track("methodStatic");
    }

    /**
     * SynchronizedVerify is lock
     */
    public synchronized static void methodStaticContrast() {
        track("methodStaticContrast");
    }


    /**
     * 循环输出一段内容
     **/
    private static void track(String callerName) {
        for (int i = 0; i < 3; i++) {
            System.out.println(
                    callerName + ":" + i + "\t|" +
                            Thread.currentThread().getName());

            Thread.yield();
        }
    }
}
