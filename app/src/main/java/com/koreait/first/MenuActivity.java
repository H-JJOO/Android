package com.koreait.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.koreait.first.ch07.BookPersonActivity;
import com.koreait.first.ch10.DailyBoxofficeActivity;
import com.koreait.first.picsum.PicsumActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    //전화기능
    public void call(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel: "));
        startActivity(intent);
    }


    public void moveToActivity(View v) {
        int id = v.getId();
        Class c = null;

        switch (id) {
            case R.id.menuBtn1:
                c = MainActivity.class;
                break;
            case R.id.menuBtn2:
                c = LinearActivity.class;
                break;
            case R.id.menuBtn3:
                c = ConstraintActivity.class;
                break;
            case R.id.menuBtn4:
                c = WriteActivity.class;
                break;
            case R.id.menuBtn5:
                c = BookPersonActivity.class;
                break;
            case R.id.menuBtn6:
                c = ImageViewActivity.class;
                break;
            case R.id.menuBtn7:
                c = PicsumActivity.class;
                break;
            case R.id.menuBtn8:
                c = DailyBoxofficeActivity.class;
                break;

        }

//        if (id == R.id.menuBtn1) {
//            c = MainActivity.class;
//        } else if (id == R.id.menuBtn2) {
//            c = LinearActivity.class;
//        } else if (id == R.id.menuBtn3) {
//            c = ConstraintActivity.class;
//        }

        Intent intent = new Intent(this, c);
        startActivity(intent);

    }


    // 참고용
    public void moveToActivityWithText(View v) {
        TextView tv = (TextView)v;
        String text = (String)tv.getText();
        Log.i("myLog", text);

        Class c = null;

// 주소값 비교할때는 == 씀, 문자열 비교할때는 equals

        switch (text) {
            case "메인":
                c = MainActivity.class;
                break;
            case "리니어레이아웃":
                c = LinearActivity.class;
                break;
            case "제약레이아웃":
                c = ConstraintActivity.class;
                break;
        }

//        if ("메인".equals(text)) {
//            c = MainActivity.class;
//        } else if ("리니어레이아웃".equals(text)) {
//            c = LinearActivity.class;
//        } else if ("제약레이아웃".equals(text)) {
//            c = ConstraintActivity.class;
//        }

        Intent intent = new Intent(this, c);
        startActivity(intent);
    }



}