package com.songsong.fileselector;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODEB = 1;
    private static final String TAG = "MainActivity";
    private Button btn2;
    private ListView mListView;
    private FileAdapter adapter;

    private List<String> mList = new ArrayList<>();
    private List<FileBean> mFileBeans = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 123:
                    adapter = new FileAdapter(MainActivity.this, R.layout.file_item, mFileBeans);
                    ListView listView = findViewById(R.id.list_id);
                    listView.setAdapter(adapter);
                    if (mFileBeans != null) {
                        btn2.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.list_id);
        Button btn1 = findViewById(R.id.btn_fileSelect1);
        btn2 = findViewById(R.id.btn_fileoutput);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select1();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final FileBean fileBean = mFileBeans.get(position);
                Toast.makeText(MainActivity.this, fileBean.getFilepath(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("确认退出吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mFileBeans.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });


    }

    private void select1() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件"), FILE_SELECT_CODEB);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "没有找到想要的文件", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                Log.d(TAG, "文件路径：" + uri.getPath());

                if (!uri.getPath().contains("txt") && !uri.getPath().contains("csv")) {
                    Toast.makeText(this, "非法文件", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mList.contains(uri.getPath())) {
                    mList.add(uri.getPath());
                    FileBean fileBean = new FileBean(uri.getPath());
                    mFileBeans.add(fileBean);
                } else {
                    Toast.makeText(this, "文件已存在", Toast.LENGTH_SHORT).show();
                }
                Message message = new Message();
                message.what = 123;
                mHandler.sendMessage(message);
            }
        }
    }


}
