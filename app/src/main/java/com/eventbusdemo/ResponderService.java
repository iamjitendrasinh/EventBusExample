package com.eventbusdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Admin on 12-Dec-17.
 */

public class ResponderService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Register EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return START_STICKY;
    }

    public void onEvent(MainActivity.MessageEvent event) {
        String message = event.getMessage();
        // process data
        String processed_message = new StringBuilder(event.getMessage()).reverse().toString();
        // post : send to activity
        EventBus.getDefault().post(new ServiceMessageEvent(processed_message));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // unregister EventBus
        EventBus.getDefault().unregister(this);
    }

    public class ServiceMessageEvent {
        private String message;

        public ServiceMessageEvent(String text) {
            message = text;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String msg) {
            message = msg;
        }
    }
}