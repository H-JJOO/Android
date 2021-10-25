package com.koreait.first.ch10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.koreait.first.R;
import com.koreait.first.ch10.searchmoviemodel.MovieInfoResultBodyVO;
import com.koreait.first.ch10.searchmoviemodel.MovieInfoResultVO;
import com.koreait.first.ch10.searchmoviemodel.MovieInfoVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView tvMovieNm;
    private TextView tvMovieNmEn;
    private TextView tvShowTm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {//한번만 하면 되는것들
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        String movieCd = intent.getStringExtra("movieCd");
        // TODO movieCd 값 전 Activity (MovieListActivity)에서 전달 받는다.
        Log.i("myLog", "detail - movieCd : " + movieCd);

        getData(movieCd);

        tvMovieNm = findViewById(R.id.tvMovieNm);
        tvMovieNmEn = findViewById(R.id.tvMovieNmEn);
        tvShowTm = findViewById(R.id.tvShowTm);

    }

    private void getData(String movieCd) {
        Retrofit rf = new Retrofit.Builder()//객체화
                .baseUrl("https://www.kobis.or.kr/kobisopenapi/webservice/rest/")//사이트의 기본주소(와 통신하겠다.)
                .addConverterFactory(GsonConverterFactory.create())//json 형태의 문자열을 자바 문자열로 변환
                .build();

        KobisService service = rf.create(KobisService.class);
        final String KEY = "de390418f9162804bdcd0fe1cffa546e";
        Call<MovieInfoResultBodyVO> call = service.searchMovieInfo(KEY, movieCd);
        call.enqueue(new Callback<MovieInfoResultBodyVO>() {
            @Override
            public void onResponse(Call<MovieInfoResultBodyVO> call, Response<MovieInfoResultBodyVO> res) {
                if (res.isSuccessful()) {//state : 200 - 통신 성공!!
                    MovieInfoResultBodyVO result = res.body();//모든 정보 담겨있음

                    //2줄로 풀어쓰기
                    MovieInfoResultVO result2 = result.getMovieInfoResult();
                    MovieInfoVO data = result2.getMovieInfo();

                    //1줄로 쓰기
                    MovieInfoVO data2 = result.getMovieInfoResult().getMovieInfo();

                    //전부 1줄
                    MovieInfoVO data3 = res.body().getMovieInfoResult().getMovieInfo();

                    //data 나 data2 나 같은 주소값

                    tvMovieNm.setText(data.getMovieNm());
                    tvMovieNmEn.setText(data.getMovieNmEn());
                    tvShowTm.setText(data.getShowTm());


                } else {

                }
            }

            @Override
            public void onFailure(Call<MovieInfoResultBodyVO> call, Throwable t) {//baseUrl이 틀리거나, 그 서버가 죽어있을 때

            }
        });

    }
}