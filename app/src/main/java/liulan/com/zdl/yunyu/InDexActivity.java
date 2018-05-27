package liulan.com.zdl.yunyu;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InDexActivity extends AppCompatActivity {

    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_dex);
        myHandler = new MyHandler();
        myHandler.sendEmptyMessageDelayed(101, 2000);

    }

    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 101) {
                startActivity(new Intent(InDexActivity.this, GuideActivity.class));
                finish();
            }
        }
    }

}
