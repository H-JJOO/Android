package com.koreait.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class WriteActivity extends AppCompatActivity {

    private EditText etMsg;
    private Button btnSend;
    private RecyclerView rvList;// view 영역

    private List<String> msgList;// data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);//activity_write.xml

        etMsg = findViewById(R.id.etMsg);
        btnSend = findViewById(R.id.btnSend);
        rvList = findViewById(R.id.rvList);

        msgList = new LinkedList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(llm);//출력정보(위에서 아래로 vertical), 레이아웃 세팅

        SimpleTextAdapter sta = new SimpleTextAdapter(msgList);
        rvList.setAdapter(sta);//

        //인터페이스 객체화 불가능
        //1. class 작성 필요

//        View.OnClickListener event2 = new MyOnClickListener();
//        btnSend.setOnClickListener(event2);
//
//        //2. 변수 할당 필요
//        View.OnClickListener event = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {}
//        };
//        btnSend.setOnClickListener(event);

        //3. 가장 간략하게 작성

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //콜백 메소드 (Call Back)
                String msg = etMsg.getText().toString();
                Log.i("myLog", msg);//입력한 값 가져옴
                etMsg.setText("");//입력한 값 빈칸으로 바꿈
                msgList.add(msg);//뷰 영역에 추가
                sta.notifyDataSetChanged();
            }
        });
    }
}

//class MyOnClickListener implements View.OnClickListener {
//
//    @Override
//    public void onClick(View v) {
//        Log.i("myLog", "111111");
//    }
//}

class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.MyViewHolder> {

    private List<String> list;

    SimpleTextAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_textview, parent, false);
        return new SimpleTextAdapter.MyViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i("myLog", "position : " + position);
        String str = list.get(position);
        holder.tvMsg.setText(str);
    }

    @Override
    public int getItemCount() {
        Log.i("myLog", "getItemCount : " + list.size());
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMsg;

        public MyViewHolder(View v) {
            super(v);
            tvMsg = v.findViewById(R.id.tvMsg);
        }
    }
}