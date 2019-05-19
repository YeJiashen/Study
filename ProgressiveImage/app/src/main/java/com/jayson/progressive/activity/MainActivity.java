package com.jayson.progressive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jayson.progressive.R;

/**
 * @date 2019/5/16
 * @author Ye Jiashen
 * @description: 主Activity，一切的入口
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.local_progressive_img_button).setOnClickListener(this);
        findViewById(R.id.net_progressive_img_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent();
        switch (view.getId()) {
            case R.id.local_progressive_img_button:
                i.setClass(this, LocalProgressiveActivity.class);
                break;
            case R.id.net_progressive_img_button:
                i.setClass(this, NetProgressiveActivity.class);
                break;
            default:
                break;
        }
        startActivity(i);
    }
}
