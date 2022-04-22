package com.yeck.classsqlite1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textViewName, textViewPhoneNumber, textViewAddress;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewName = findViewById(R.id.textViewName);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        uri = Uri.parse("content://com.yeck.classsqlite1.provider");
        getContentResolver().registerContentObserver(uri, true, new MyContentObselver(new Handler()));
    }

    public void addBtnClicked(View view) {
        if (textViewName.getText().toString().isEmpty() || textViewPhoneNumber.getText().toString().isEmpty() || textViewAddress.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "请填写所有信息", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User();
            user.name = textViewName.getText().toString();
            user.address = textViewAddress.getText().toString();
            user.phoneNumber = textViewPhoneNumber.getText().toString();
            UserDatabase.getInstance(MainActivity.this).getUserDao().insert(user);
            Toast.makeText(MainActivity.this, "已添加", Toast.LENGTH_SHORT).show();
            getContentResolver().notifyChange(uri, null);


        }
        HideKeyboard();
    }

    public void updateBtnClicked(View view) {
        if (textViewName.getText().toString().isEmpty() || textViewPhoneNumber.getText().toString().isEmpty() || textViewAddress.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "请填写所有信息", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User();
            user.name = textViewName.getText().toString();
            user.address = textViewAddress.getText().toString();
            user.phoneNumber = textViewPhoneNumber.getText().toString();
            UserDatabase.getInstance(MainActivity.this).getUserDao().update(user);
            Toast.makeText(MainActivity.this, "已更新", Toast.LENGTH_SHORT).show();
            getContentResolver().notifyChange(uri, null);
        }
        HideKeyboard();
    }

    public void delBtnClicked(View view) {
        if (textViewPhoneNumber.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "请至少填写手机号", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User();
            if (!textViewName.getText().toString().isEmpty())
                user.name = textViewName.getText().toString();
            if (!textViewAddress.getText().toString().isEmpty())
                user.address = textViewAddress.getText().toString();
            user.phoneNumber = textViewPhoneNumber.getText().toString();
            UserDatabase.getInstance(MainActivity.this).getUserDao().delete(user);
            Toast.makeText(MainActivity.this, "已删除", Toast.LENGTH_SHORT).show();
            getContentResolver().notifyChange(uri, null);
        }
        HideKeyboard();
    }

    @SuppressLint("SetTextI18n")
    public void queryBtnClicked(View view) {
        if (textViewPhoneNumber.getText().toString().isEmpty() && textViewName.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "请至少填写手机号或姓名", Toast.LENGTH_SHORT).show();
        } else {
            User user = null;
            if (!textViewName.getText().toString().isEmpty()) {
                user = UserDatabase.getInstance(MainActivity.this).getUserDao().getUser2(textViewName.getText().toString());
            }

            if (!textViewPhoneNumber.getText().toString().isEmpty()) {
                user = UserDatabase.getInstance(MainActivity.this).getUserDao().getUser(textViewPhoneNumber.getText().toString());
            }
            TextView textView = findViewById(R.id.queryShow);
            if (user == null) {
                Toast.makeText(MainActivity.this, "这个人不存在你的通讯录中哦", Toast.LENGTH_SHORT).show();
            } else {
                textView.setText("姓名：" + user.name + "\n手机号：" + user.phoneNumber + "\n地址：" + user.address);
                Toast.makeText(MainActivity.this, "已查询", Toast.LENGTH_SHORT).show();
            }
        }
        HideKeyboard();
    }

    public void HideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),

                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    class MyContentObselver extends ContentObserver {
        public MyContentObselver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Toast.makeText(MainActivity.this, "Data changed!", Toast.LENGTH_LONG).show();
        }
    }
}
