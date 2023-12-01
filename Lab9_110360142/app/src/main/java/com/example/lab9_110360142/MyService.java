package com.example.lab9_110360142;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class MyService extends Service {
    static Boolean flag = false;

    private  int h=0, m=0, s=0; //計時器數值

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        flag = intent.getBooleanExtra("flag", false);

        //使用Thread來計時
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        Thread.sleep(1000); //使用 Thread 來計算秒數，延遲 1 秒

                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    s++; //計數器+1
                    if (s >= 60) {
                        s=0;
                        m++;
                        if (m >= 60) {
                            m=0;
                            h++;
                        }
                    }
                    //}
                    Intent i = new Intent("MyMessage"); //產生帶 MyMessage 識別字串的 Intent
                    Bundle bundle = new Bundle();
                    bundle.putInt("H", h);
                    bundle.putInt("M", m);
                    bundle.putInt("S", s);

                    i.putExtras(bundle); //用Bundle打包後放入

                    sendBroadcast(i); //發送廣播
                }
            }
        }).start();
        return START_STICKY;
    }
}