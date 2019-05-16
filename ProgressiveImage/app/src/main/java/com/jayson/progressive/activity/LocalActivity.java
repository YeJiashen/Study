package com.jayson.progressive.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jayson.progressive.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @date 2019/5/16
 * @author Ye Jiashen
 * @description: 本地图片渐进式加载
 */
public class LocalActivity extends AppCompatActivity {
    private ImageView img;
    private ExecutorService es = Executors.newFixedThreadPool(1);
    private boolean isFinish = false;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        img = findViewById(R.id.img_local);
        img.setOnClickListener(v -> loadProgressiveImage());
    }

    private void loadProgressiveImage() {
        if (isLoading) return;
        isLoading = true;
        es.execute(() -> {
            InputStream is = null;
            try {
                int size = getImageLength();
                is = getImgInputStream();
                byte[] stream = new byte[size];
                int offset = 0;

                while (!isFinish) {
                    byte[] progressiveStream = getBytesProgressive(is);
                    System.arraycopy(progressiveStream, 0, stream, offset, progressiveStream.length);
                    offset += progressiveStream.length;
                    Bitmap bm = getTempBitmap(stream, offset);
                    showImageProgressive(bm);
                }
            }catch (IOException ignore) {

            }finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void showImageProgressive(Bitmap bm) {
        runOnUiThread(() -> img.setImageBitmap(bm));
    }

    private Bitmap getTempBitmap(byte[] byteArr, int offset) {
        byte[] temp = new byte[offset];
        System.arraycopy(byteArr, 0, temp, 0, offset);
        temp[offset - 2] = -1;
        temp[offset - 1] = -39;
        return BitmapFactory.decodeByteArray(temp, 0, offset);
    }

    private InputStream getImgInputStream() {
        InputStream is = null;
        try {
            is = getAssets().open("progressive_image_001.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    private int getImageLength() {
        InputStream is = getImgInputStream();
        int length = input2byte(is).length;
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }

    private byte[] getBytesProgressive(InputStream is) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        //这里设置每次读取的数量,设置小一点是为了让效果更明显
        byte[] buffer = new byte[10]; // 用数据装
        int len = -1;
        //要实现比较理想的渐进式加载效果,其实不应该写死每次读取量,应该是根据FFDA来判断读到第几帧了
        if ((len = is.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        } else {
            isFinish = true;
            is.close();
        }

        outstream.close();
        // 关闭流一定要记得。
        return outstream.toByteArray();
    }

    private byte[] input2byte(InputStream inStream) {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] in2b;
        try {
            byte[] buff = new byte[1024];
            int rc = 0;
            while ((rc = inStream.read(buff, 0, 1024)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            in2b = swapStream.toByteArray();
        } catch (IOException ignore) {
            in2b = new byte[0];
        } finally {
            try {
                swapStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return in2b;
    }
}
