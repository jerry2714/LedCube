package gui;

/**
 * ThreadLoop提供了可以開啟一個執行緒並在執行緒中執行一個無窮迴圈的功能。
 * 使用方式如下:
 *1. 在子類別實作 public void exute()
 * 2. 像一般的執行緒一樣呼叫start()開始反覆執行exute()
 * 3.呼叫 setTimeInterval(long)可設定迴圈每一次執行exute()的間隔時間，預設為 0
 * Created by Jerry on 2017/2/1.
 */
public abstract class ThreadLoop extends Thread
{
    private long timeInterval = 0;
    public ThreadLoop(){}
    @Override
    public void run()
    {
        System.out.println("loop start");
        long nano = System.nanoTime();

        while(true)
        {
            while(System.nanoTime() - nano < timeInterval);
            nano = System.nanoTime();
            execute();
        }
    }

    /**
     * 設定間隔多少時間執行一次excute()
     * @param t  間隔時間，以奈秒為單位
     */
    public void setTimeInterval(long t)
    {
        timeInterval = t;
    }

    abstract public void execute();
}
