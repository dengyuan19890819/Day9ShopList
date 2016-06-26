package com.example.administrator.day9shoplist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.day9shoplist.adapter.MyAdapter;
import com.example.administrator.day9shoplist.httpUtils.HttpUtils;
import com.example.administrator.day9shoplist.shop.Shop;


public class MainActivity extends Activity {

    //private String shopJsonUrl = "http://api.qingchifan.com/api/event/city.json?access_token=6916a7ab1f71e43ac4eadf40a51b4ab1E4DA4EE79DE4C64C449D09318FE0784F&basicCheck=0&city=0755&multi=0&gender=0&time=0&age=0&constellation=0&occupation=0&start=0&size=20&apiVersion=2.8.0";

    private static List<Shop> list = new ArrayList<>();

    private ListView listview;
    static MyAdapter myadapter;

    private ProgressDialog pd;

    private int refreshState = 0;

    // Log.d("TAG", list.toString());

    static int currentPage = 0;

    private static String getUrl() {

        String shopJsonUrl = "http://api.qingchifan.com/api/event/city.json?access_token=6916a7ab1f71e43ac4eadf40a51b4ab1E4DA4EE79DE4C64C449D09318FE0784F&basicCheck=0&city=0755&multi=0&gender=0&time=0&age=0&constellation=0&occupation=0&"
                + "start=" + currentPage + "&size=20&apiVersion=2.8.0";
        return shopJsonUrl;
    }

    // 加载更多
    private void loadMore() {
        currentPage++;
        startJsonPaser();
    }

    // 刷新
    private void flushData() {
        currentPage = 0;
        startJsonPaser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listView1);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pd.dismiss();
            }

        });
        // 滚动
        listview.setOnScrollListener(new OnScrollListener() {
            /*
             * switch(scrollState){ case
             * OnScrollListener.SCROLL_STATE_FLING:break;//手松开 2 case
             * OnScrollListener.SCROLL_STATE_IDLE:break;//静止态 0 case
             * OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:break;//滚动态 1 }
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {// 静止时
                    if (refreshState == 1) {
                        flushData();// 刷新数据
                    } else if (refreshState == -1) {
                        loadMore();// 加载更多
                    }
                }
            }

            // 下拉刷新最新数据。上拉加载更多的项目
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {// 下拉，到头部
                    refreshState = 1;
                } else if (firstVisibleItem == totalItemCount - visibleItemCount) {// 到了底部
                    refreshState = -1;
                }

            }
        });

        findViewById(R.id.bu_load).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                startJsonPaser();
            }
        });
    }

    private void startJsonPaser() {
        new AsyncTask<String, String, List<Shop>>() {

            protected void onPreExecute() {
                if (HttpUtils.isNetwork(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "网络连接正常", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();

                }
                // 进度条
                pd = ProgressDialog.show(MainActivity.this, "友情提示", currentPage == 0 ? "加载更多" : "正在刷新");
            }

            protected List<Shop> doInBackground(String... params) {

                byte[] loadBytes = HttpUtils.loadBytes(MainActivity.this, params[0], null);

                if (loadBytes == null) {
                    return null;
                }

                if (currentPage == 0) {
                    list.clear();
                }

                String jsonStr = new String(loadBytes, 0, loadBytes.length);

                list = HttpUtils.jsonPaser(jsonStr);

                Log.d("TAG", list.toString());

                return list;
            }

            protected void onPostExecute(List<Shop> result) {
                // 已经加载完毕，如果弹出进度再显示，则将其关闭
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }

                myadapter = new MyAdapter(list, MainActivity.this);

                listview.setAdapter(myadapter);

                myadapter.notifyDataSetChanged();
            }

            @Override
            protected void onProgressUpdate(String... values) {
                // TODO Auto-generated method stub
                super.onProgressUpdate(values);

                Toast.makeText(MainActivity.this, values[0], Toast.LENGTH_SHORT).show();
            }
        }.execute(getUrl());
    }


}
