package com.koreait.first.ch10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koreait.first.R;
import com.koreait.first.ch10.searchmoviemodel.ActorVO;
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
    private RecyclerView rvActorList;
    private ActorAdapter adapter;


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
        rvActorList = findViewById(R.id.rvActorList);
        //어뎁터 만들고
        adapter = new ActorAdapter();
        //리사이클에 연결하고 값 넣어주기
        rvActorList.setAdapter(adapter);



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

                    List<ActorVO> list = data.getActors();
                    adapter.setList(list);

//                    adapter.setList(data.getActors());
                    adapter.notifyDataSetChanged();


                } else {

                }
            }

            @Override
            public void onFailure(Call<MovieInfoResultBodyVO> call, Throwable t) {//baseUrl이 틀리거나, 그 서버가 죽어있을 때

            }
        });

    }
}

class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.MyViewHolder> {

    private List<ActorVO> list;

    public void setList(List<ActorVO> list) {//setter
        this.list = list;
    }

    @NonNull
    @Override
    //Layout 만드는거
    //Layout 쓸거 객체화(틀을 만들어거서 계속 쓸꺼임)
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((parent.getContext()));
        View v = inflater.inflate(R.layout.item_actor, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    //아이템 갯수 만큼 호출(for문), 계속해서 position 값 이 바뀜 0, 1, 2, 3, ...
    // position 값이 바뀌기 때문에 list에 넣을수 있음
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ActorVO vo = list.get(position);
        holder.setItem(vo);
    }

    @Override
    //아이템 갯수
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPeopleNm;
        private TextView tvPeopleNmEn;
        private TextView tvCast;
        private TextView tvCastEn;

        public MyViewHolder(View v) {//item_actor.xml 주소값 가져온다
            super(v);

            tvPeopleNm = v.findViewById(R.id.tvPeopleNm);
            tvPeopleNmEn = v.findViewById(R.id.tvPeopleNmEn);
            tvCast = v.findViewById(R.id.tvCast);
            tvCastEn = v.findViewById(R.id.tvCastEn);

        }

        public void setItem(ActorVO vo) {
            tvPeopleNm.setText(vo.getPeopleNm());
            tvPeopleNmEn.setText(vo.getPeopleNmEn());
            tvCast.setText(vo.getCast());
            tvCastEn.setText(vo.getCastEn());
        }
    }

}