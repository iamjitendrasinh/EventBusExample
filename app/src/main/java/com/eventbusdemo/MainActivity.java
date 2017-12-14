package com.eventbusdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventBus = EventBus.getDefault();
        findViewById(R.id.txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent("HIII"));
            }
        });
        // Register EventBus
        eventBus.register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister EventBus
        if (eventBus != null && eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }

    /* subscriber method */
    @Subscribe
    public void onEvent(ResponderService.ServiceMessageEvent event) {
        String data = event.getMessage();
        Toast.makeText(this, data + " a- ", Toast.LENGTH_SHORT).show();
        // do something with this message
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(MainActivity.this, event.message, Toast.LENGTH_SHORT).show();
		
    }

    public class MessageEvent {
        private String message;

        public MessageEvent(String text) {
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
