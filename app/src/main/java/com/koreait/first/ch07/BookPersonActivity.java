package com.koreait.first.ch07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.koreait.first.R;

public class BookPersonActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_person);

        //id 값으로 연결
        rvList = findViewById(R.id.rvList);
        //어뎁터 생성
        adapter = new PersonAdapter();

        //기본적으로 위에서 아래로 내려오는 (세로)
        rvList.setLayoutManager(new LinearLayoutManager(this));

        //어뎁터 연결
        rvList.setAdapter(adapter);

        adapter.addItem(new Person("홍길동", 20));
        adapter.addItem(new Person("난다김", 22));
        adapter.addItem(new Person("블랙보리", 24));
        //어뎁터 연결 후 값이 입력되었기 때문에 notify 로 찾아줘야한다.
        adapter.notifyDataSetChanged();

    }
}
