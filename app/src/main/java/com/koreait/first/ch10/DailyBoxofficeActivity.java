package com.koreait.first.ch10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.koreait.first.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DailyBoxofficeActivity extends AppCompatActivity {

    private DatePicker dpTargetDt;//세트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_boxoffice);
        dpTargetDt = findViewById(R.id.dpTargetDt);//세트


    }

    private void network(String targetDt) {
        Retrofit rf = new Retrofit.Builder()//객체화
                .baseUrl("https://www.kobis.or.kr/kobisopenapi/webservice/rest/")//사이트의 기본주소(와 통신하겠다.)
                .addConverterFactory(GsonConverterFactory.create())//json 형태의 문자열을 자바 문자열로 변환
                .build();

        KobisService service = rf.create(KobisService.class);

        final String KEY = "de390418f9162804bdcd0fe1cffa546e";
        Call<BoxOfficeResultBodyVO> call = service.boxofficeSearchDailyBoxOfficeList(KEY, targetDt);

        //비동기처리
        call.enqueue(new Callback<BoxOfficeResultBodyVO>() {
            @Override
            public void onResponse(Call<BoxOfficeResultBodyVO> call, Response<BoxOfficeResultBodyVO> res) {
                if (res.isSuccessful()) {
                    BoxOfficeResultBodyVO vo = res.body();
                    BoxOfficeResultVO resultVO = vo.getBoxOfficeResult();
                    List<DailyBoxOfficeVO> list = resultVO.getDailyBoxOfficeList();

                    Log.i("myLog", list.size() + "개");

                    for (DailyBoxOfficeVO item : list) {
                        Log.i("myLog", item.getMovieNm());
                    }
                }
            }

            @Override
            public void onFailure(Call<BoxOfficeResultBodyVO> call, Throwable t) {

            }
        });
    }

    public void clkSearch(View v) {
        int day = dpTargetDt.getDayOfMonth();
        int mon = dpTargetDt.getMonth() + 1;
        int year = dpTargetDt.getYear();

        String date = String.format("%s%02d%02d", year, mon, day);
        network(date);

        Log.i("myLog", date);

        /*
        //Calendar 객체
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        //날짜 설정하기
        Calendar c = Calendar.getInstance();//싱글톤 객체가 넘어옴, 무조건 하나만 만들겠다.
        c.set(year, mon, day);

        String date = sdf.format(c.getTime());
        Log.i("myLog", date);
         */
    }

}