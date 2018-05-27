package liulan.com.zdl.yunyu;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

public class MainActivity extends AppCompatActivity {

    PullToRefreshLayout pulltorefresh;
    WebView collectWeb;
    private java.lang.String adUrl = "http://192.168.252.111:8086/";

    LoadingDialog loadingDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pulltorefresh = findViewById(R.id.pulltorefresh);
        collectWeb = findViewById(R.id.native_web);
        initpulltorefresh();
        webset();

        loadingDialogFragment = new LoadingDialog(this);
        loadingDialogFragment.setCancelable(false);


    }

    private void webset() {
        WebSettings settings = collectWeb.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        collectWeb.loadUrl(adUrl);

        collectWeb.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {
                    pulltorefresh.finishRefresh();
                }
            }
        });
        collectWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (loadingDialogFragment != null) {
                    loadingDialogFragment.show();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (loadingDialogFragment != null) {
                    loadingDialogFragment.dismiss();
                }
            }

        });

        collectWeb.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //这里处理返回键事件
                        if (collectWeb.canGoBack()) {
                            collectWeb.goBack();
//                            Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }


    private void initpulltorefresh() {
        pulltorefresh.setCanLoadMore(false);
        pulltorefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                collectWeb.loadUrl(adUrl);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束刷新
//                        pulltorefresh.finishRefresh();


                    }
                }, 2000);
            }

            @Override
            public void loadMore() {

            }
        });
    }

    private long timeMillis;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - timeMillis) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                timeMillis = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
