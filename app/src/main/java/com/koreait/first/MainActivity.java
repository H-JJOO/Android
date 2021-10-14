package com.koreait.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ddd(View v) {
        //누를 때 마다 1씩 증가
        if(v instanceof TextView) {//v에 담겨있는 객체주소값을 TextView 타입으로 저장 할 수 있으면 true 없으면 false
            TextView tv = (TextView)v;
            String val = (String)tv.getText();//Text 값 가져오기
            int intVal = Integer.parseInt(val);//문자열인 Text 값을 int로 형변환
            intVal += 1;//1씩증가
            String parseStrVal = String.valueOf(intVal);//int 형변환 된걸 다시 String으로 형변환
            tv.setText(parseStrVal);
        }

//        TextView tv = (TextView)v;

//        Toast toast = Toast.makeText(this, tv.getText(), Toast.LENGTH_LONG);//context 보이면 this 적어라
//        toast.show();//밑에꺼랑 같은 코드

//        Toast.makeText(this, tv.getText(), Toast.LENGTH_LONG).show();//체인기법, Toast.LENGTH_SHORT 은 좀더 짧음
        //인스턴스 메소드는 static 안붙은거, 가능하면 static 붙혀라, 절대안되는 경우도 있다.
        //앞쪽 모양 대문자시작은 클래스명이라는거고 클래스명. 해서 메소드 호출가능한건 static 메소드뿐이다. 인스턴스 메소드는 new가 있어야 한다.
//        CharSequence cs = tv.getText() + "를 클릭했어요";
//        Toast.makeText(this, cs, Toast.LENGTH_LONG).show();
//        String str = (String)tv.getText();
//        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    // 이벤트 연결 (event binding) 버튼 클릭시 실행될 메소드 연결
    public void clkBtn(View v) {//view 가 붙어있는 것들의 부모, 모든 위젯들의 객체 주소값을 받을 수 있다.
        // v.getTest();
        Button btn = (Button)v;
        String btnText = (String)btn.getText();
        Toast.makeText(this, btnText + "를 클릭했어요.", Toast.LENGTH_LONG).show();
    }



}