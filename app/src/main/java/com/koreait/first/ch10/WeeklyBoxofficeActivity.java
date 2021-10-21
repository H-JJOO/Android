package com.koreait.first.ch10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

public class WeeklyBoxofficeActivity extends AppCompatActivity {

    private DailyBoxofficeAdapter adapter;

    private DatePicker dpTargetDt;
    private RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_boxoffice);

        adapter = new DailyBoxofficeAdapter();

        dpTargetDt = findViewById(R.id.dpTargetDt);
        rvList = findViewById(R.id.rvList);
        rvList.setAdapter(adapter);
    }

    public void network(String targetDt) {
        Retrofit rf = new Retrofit.Builder().baseUrl("https://www.kobis.or.kr/kobisopenapi/webservice/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KobisService service = rf.create(KobisService.class);

        final String KEY = "de390418f9162804bdcd0fe1cffa546e";
        Call<BoxOfficeResultBodyVO> call = service.searchWeeklyBoxOfficeList(KEY, targetDt);

        call.enqueue(new Callback<BoxOfficeResultBodyVO>() {
            @Override
            public void onResponse(Call<BoxOfficeResultBodyVO> call, Response<BoxOfficeResultBodyVO> res) {
                if (res.isSuccessful()) {
                    BoxOfficeResultBodyVO vo = res.body();

                    BoxOfficeResultVO resultVO = vo.getBoxOfficeResult();
                    List<DailyBoxOfficeVO> list = resultVO.getDailyBoxOfficeList();

                    Log.i("myLog", list.size() + "개");

                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<BoxOfficeResultBodyVO> call, Throwable t) {

            }
        });
    }


    public void clkSearch(View v) {
        int day = dpTargetDt.getDayOfMonth();
        int mon = dpTargetDt.getMonth();
        int year = dpTargetDt.getYear();

        String date = String.format("%s%02d%02d", year, mon, day);
        network(date);

        Log.i("myLog", date);
    }
}