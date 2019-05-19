package com.jayson.progressive.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.jayson.progressive.App;
import com.jayson.progressive.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ye Jiashen
 * @date 2019/5/16
 * @description: 本地图片渐进式加载
 */
public class LocalProgressiveActivity extends AppCompatActivity {
    private static String TAG = "LocalProgressiveActivity";
    private String imgName = "progressive_image_001.jpg";
    private ImageView img;
    private boolean isFinish = false;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_progressive);
        img = findViewById(R.id.img_local);
        findViewById(R.id.title_local_progressive).setOnClickListener(v -> loadProgressiveImage());
    }

    /**
     * 开始使用渐进的方式加载图片
     */
    private void loadProgressiveImage() {
        if (isLoading) return;
        isLoading = true;
        App.getEs().execute(() -> {
            InputStream is = null;
            try {
                int size = (int) getAssets().openFd(imgName).getLength();
                is = getAssets().open(imgName);
                byte[] stream = new byte[size];
                int offset = 0;

                while (!isFinish) {
                    byte[] progressiveStream = getBytesProgressive(is);
                    System.arraycopy(progressiveStream, 0, stream, offset, progressiveStream.length);
                    offset += progressiveStream.length;
                    Bitmap bm = getTempBitmap(stream, offset);
                    showImageProgressive(bm);
                    Log.d(TAG, "I am loading image: " + offset + "/" + size);
                }
            } catch (IOException ignore) {
            } finally {
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

    @Override
    public void finish() {
        isFinish = true;
        super.finish();
    }

    /**
     * 将渐进式加载中的阶段性Bitmap显示到图片控件上的方法
     *
     * @param bm 阶段性Bitmap
     */
    private void showImageProgressive(Bitmap bm) {
        runOnUiThread(() -> img.setImageBitmap(bm));
    }

    /**
     * 获取阶段性的Bitmap, 其实就是把最后四位修改为FFDA然后作为bitmap编码
     *
     * @param byteArr 阶段性字节数组
     * @param offset  需要读取的长度, 与传入的字节数组的有效位数一致
     * @return 阶段性Bitmap(渐进式加载中每次刷新显示的图片)
     */
    private Bitmap getTempBitmap(byte[] byteArr, int offset) {
        byte[] temp = new byte[offset];
        System.arraycopy(byteArr, 0, temp, 0, offset);
        temp[offset - 2] = -1;  // FF
        temp[offset - 1] = -39; // D9
        return BitmapFactory.decodeByteArray(temp, 0, offset);
    }

    /**
     * 渐进式的加载图片数据流的一部分, 用于模拟每次从服务器上请求到的图片的一部分
     *
     * @param is 图片文件输入流
     * @return 输入流对应的字节数组
     * @throws IOException 文件操作对应的IO异常
     */
    private byte[] getBytesProgressive(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 这里设置每次读取的数量,设置小一点是为了让效果更明显
        byte[] buffer = new byte[100];
        int len = -1;
        // 要实现比较理想的渐进式加载效果,其实不应该写死每次读取量
        // 应该是根据FFDA来判断读到第几帧了
        if ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        } else {
            isFinish = true;
            is.close();
        }
        os.close();
        return os.toByteArray();
    }
}
