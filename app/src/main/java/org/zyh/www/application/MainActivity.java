package org.zyh.www.application;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import entity.Entity;
import hprose.client.HproseClient;
import hprose.client.HproseHttpClient;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final int SUCCESS = 0x01;
    private Button btn1;
    private Button btn2;
    private TextView content;
    // TODO url goes here!
    public static final String URL = "http://192.168.31.122/mubizserver/api/gzcontroller.php";
    // TODO method goes here!
    public static final String METHOD = "GZ_get_task";
    // TODO parameters goes here!
    private Object[] parameters = new Object[]{ "token",1,100 };

    private Entity[] entities;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    Entity[] list = (Entity[]) msg.obj;
                    StringBuilder text = new StringBuilder();
                    for(Entity entity :list){
                        text.append(entity.toString());
                    }
                    content.setText(text.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setupWigdets();
    }

    private void init() {
        btn1 = (Button) findViewById(R.id.noparam);
        btn2 = (Button) findViewById(R.id.withparam);
        content = (TextView) findViewById(R.id.content);
    }
    private void setupWigdets() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.withparam:
                runHproseWithParams();
                break;
            case R.id.noparam:
                runHproseWithoutParams();
                break;
        }
    }

    /**
     * run hprose, use invoke method to complete net task.
     */
    private void runHproseWithParams() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HproseClient client = new HproseHttpClient();
                client.useService(URL);
                try {
                    // method is the method we use, new Object
                    entities = client.invoke(METHOD, parameters, Entity[].class);
                    Message msg = new Message();
                    msg.what = SUCCESS;
                    msg.obj = entities;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void runHproseWithoutParams() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HproseClient client = new HproseHttpClient();
                client.useService(URL);
                try {
                    // method is the method we use, new Object
                    entities = client.invoke(METHOD,Entity[].class);
                   Message msg = new Message();
                    msg.what = SUCCESS;
                    msg.obj = entities;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
