package com.jayson.progressive.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.jayson.progressive.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Ye Jiashen
 * @date 2019/5/16
 * @description: 网络图片渐进式加载
 */
public class NetProgressiveActivity extends AppCompatActivity {
    private static String TAG = "NetProgressiveActivity";
    /**
     * 支持渐进式加载的jpeg的链接
     */
    private String progressiveImgUrl = "http://www.reasoft.com/tutorials/web/img/progress.jpg";
    /**
     * OkHttp3的请求委托器
     */
    private OkHttpClient client = new OkHttpClient();
    /**
     * 图片控件
     */
    private ImageView img;
    /**
     * 服务器图片的总长度
     */
    private int totalSize = 0;
    /**
     * 用于控制请求流位置的标记
     */
    private int current = -1;
    /**
     * 用于控制流复制位置的标记
     */
    private int offset = 0;
    /**
     * 与服务器图片长度相同的字节数组, 用于存放流
     */
    private byte[] totalBytes = null;
    /**
     * 是否第一次进行请求, 如果是的话进行初始化
     */
    private boolean isInit = true;
    /**
     * 继续获取图片流
     */
    private final int GET_MORE_IMG_STREAM = 1;
    /**
     * 每次获取数据流的长度
     * <br/>由于请求时包含头尾
     * <br/>所以一共会获取256个字节的数据
     * <br/>数字越小渐进式效果越明显
     */
    private final int STEP = 255;
    /**
     * 网络请求的响应回调
     */
    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.d(TAG, "headers is:\r\n" + response.headers().toString());
            if (isInit) {
                String contentRange = response.headers().get("Content-Range");
                totalSize = getImageTotalLength(contentRange);
                totalBytes = new byte[totalSize];
                isInit = false;
                Log.d(TAG, "I am initializing, totalSize is: " + totalSize);
            }

            String contentLength = response.headers().get("Content-Length");
            int length = getContentLength(contentLength);
            Log.d(TAG, "Current contentLength is: " + length);

            byte[] bytes = response.body().bytes();
            System.arraycopy(bytes, 0, totalBytes, offset, length);
            offset += length;
            current += length;
            Log.d(TAG, "Offset is: " + offset + " and current is: " + current);
            Bitmap bm = getTempBitmap(totalBytes, offset);
            showImageProgressive(bm);
            Log.d(TAG, "Is need get more img stream: " + (offset < totalSize));
            if (offset < totalSize) {
                h.sendEmptyMessage(GET_MORE_IMG_STREAM);
            }
        }
    };
    /**
     * Handler, 用于控制是否继续获取图片
     */
    private Handler h = new Handler(msg -> {
        switch (msg.what) {
            case GET_MORE_IMG_STREAM:
                client.newCall(getRequest(current + 1, current + 1 + STEP)).enqueue(callback);
                break;
            default:
                break;
        }
        return false;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_progressive);
        img = findViewById(R.id.img_net);

        findViewById(R.id.title_local_progressive).setOnClickListener(v -> h.sendEmptyMessage(GET_MORE_IMG_STREAM));
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
     * 断点续传式获取图片, 获取请求体的方法
     *
     * @param start 开始的位置
     * @param end   结束的位置
     * @return 请求体
     */
    private Request getRequest(int start, int end) {
        Request request = new Request.Builder()
                .url(progressiveImgUrl)
                .addHeader("Range", "bytes=" + start + "-" + end)
                .build();
        return request;
    }

    /**
     * 获取本次传输返回的数据量
     *
     * @param contentLength Header中的Content-Length字段的值
     * @return 数据长度
     */
    private int getContentLength(String contentLength) {
        // Content-Length: 1025
        return Integer.parseInt(contentLength);
    }

    /**
     * 获取网络图片总大小的方法
     *
     * @param contentRange 响应头中的Content-Range字段中包含的信息
     * @return 图片总大小
     */
    private int getImageTotalLength(String contentRange) {
        // Content-Range: bytes 0-1024/44884
        String[] split = contentRange.split("/");
        String strTotal = split[split.length - 1];
        int total = Integer.parseInt(strTotal);
        return total;
    }
}
