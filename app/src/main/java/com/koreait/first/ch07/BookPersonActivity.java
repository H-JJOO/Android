package com.koreait.first.ch07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.koreait.first.R;
import com.koreait.first.Utils;

public class BookPersonActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private PersonAdapter adapter;
    private EditText etName;
    private EditText etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_person);

        //id 값으로 연결 해서 해당 id의 위젯을 찾는다
        rvList = findViewById(R.id.rvList);
        //어뎁터 객체화(생성)
        adapter = new PersonAdapter();

        //주소값 생성하고 연결
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);


        //기본적으로 위에서 아래로 내려오는 (세로)
        rvList.setLayoutManager(new LinearLayoutManager(this));

        //어뎁터 연결
        rvList.setAdapter(adapter);

//        @Override
//        public void onClick(View v) { //콜백 메소드 (Call Back)
//            String msg = etMsg.getText().toString();
//            Log.i("myLog", msg);//입력한 값 가져옴
//            etMsg.setText("");//입력한 값 빈칸으로 바꿈
//            msgList.add(msg);//뷰 영역에 추가
//            sta.notifyDataSetChanged();
//        }

        adapter.notifyDataSetChanged();

    }

    public void clkReg(View v) {
        String name = etName.getText().toString();//EditText (etName) 적힌 값을 문자열로 가져옴
        String age = etAge.getText().toString();//EditText (etAge) 적힌 값을 문자열로 가져옴
//        int intAge = Integer.parseInt(age);//문자열 -> 정수형
        int intAge = Utils.parseStringToInt(age);


        if (intAge == 0) {
            Toast.makeText(this, "문제가 발생하였습니다.", Toast.LENGTH_LONG).show();
            return;
        } else {
            Person p = new Person(name, intAge);//그릇(이름,나이 값)
            adapter.addItem(p);//어뎁터에 추가, 버튼 누를때마다 추가
            etName.setText("");//값들을 빈칸으로 주어서 삭제
            etAge.setText("");//값들을 빈칸으로 주어서 삭제
            adapter.notifyDataSetChanged();//추가 됐어~
        }
    }

}
