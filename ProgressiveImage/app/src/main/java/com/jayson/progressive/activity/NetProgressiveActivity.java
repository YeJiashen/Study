package com.jayson.progressive.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jayson.progressive.R;

/**
 * @author Ye Jiashen
 * @date 2019/5/16
 * @description: 网络图片渐进式加载
 */
public class NetProgressiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_progressive);
    }
}
