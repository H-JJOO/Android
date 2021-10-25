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
import com.koreait.first.ch10.searchmoviemodel.MovieListResultBodyVO;
import com.koreait.first.ch10.searchmoviemodel.MovieVO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListActivity extends AppCompatActivity {

    private KobisService service;
    private final String KEY = "de390418f9162804bdcd0fe1cffa546e";

    private MovieListAdapter adapter;
    private RecyclerView rvList;

    private final String ITEM_PER_PAGE = "20";
    private int curPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Retrofit rf = new Retrofit.Builder()//객체화
                .baseUrl("https://www.kobis.or.kr/kobisopenapi/webservice/rest/")//사이트의 기본주소(와 통신하겠다.)
                .addConverterFactory(GsonConverterFactory.create())//json 형태의 문자열을 자바 문자열로 변환
                .build();

        service = rf.create(KobisService.class);
        adapter = new MovieListAdapter();
        rvList = findViewById(R.id.rvList);
        //무한스크롤
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView rView, int newState) {
                if (!rView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {//끝이면서 스크롤이 멈추면 실행
                    Log.i("myLog", "스크롤 끝!");
                    getList();
                }
            }
        });
        rvList.setAdapter(adapter);

        getList();

    }

    private void getList() {//network 와 동일
        Call<MovieListResultBodyVO> call = service.searchMovieList(KEY, ITEM_PER_PAGE, curPage++);

        //비동기
        call.enqueue(new Callback<MovieListResultBodyVO>() {
            @Override
            public void onResponse(Call<MovieListResultBodyVO> call, Response<MovieListResultBodyVO> response) {
                if (response.isSuccessful()) {//200대면 호출
                    MovieListResultBodyVO result = response.body();

                    List<MovieVO> list = result.getMovieListResult().getMovieList();
                    //TODO - adapter 에 하나씩 넣어준다
                    for(MovieVO vo : list) {
                        adapter.addItem(vo);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("myLog", "통신 오류");
                }
            }

            @Override
            public void onFailure(Call<MovieListResultBodyVO> call, Throwable t) {
                Log.e("myLog", "통신 실패");
            }
        });
//        Log.i("myLog", "dddd");

    }

}

class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {
    private List<MovieVO> list = new ArrayList();//객체로 공간 할당(리스트, 콜렉션), null 가능성 없음
    //잘못된 값이 들어가지 않게 하기위해서 제네릭 사용, MovieVo 라는 타입 들어가고 리턴된다.

    public void addItem(MovieVO vo) {
        list.add(vo);//쌓이게 하기 위해서
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_movie, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MovieVO obj = list.get(position);
        holder.setItem(obj);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String movieCd = obj.getMovieCd();
                Log.i("myLog", movieCd);
                Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                intent.putExtra("movieCd", movieCd);
                v.getContext().startActivity(intent);
                //   this

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size(); // list == null ? 0 : list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMovieNm;
        private TextView tvRepNationNm;
        private TextView tvMovieNmEn;
        private TextView tvRepGenreNm;

        public MyViewHolder(View v) {
            super(v);
            tvMovieNm = v.findViewById(R.id.tvMovieNm);
            tvRepNationNm = v.findViewById(R.id.tvRepNationNm);
            tvMovieNmEn = v.findViewById(R.id.tvMovieNmEn);
            tvRepGenreNm = v.findViewById(R.id.tvRepGenreNm);
        }

        public void setItem(MovieVO vo) {
            tvMovieNm.setText(vo.getMovieNm());
            tvRepNationNm.setText(vo.getRepNationNm());
            tvMovieNmEn.setText(vo.getMovieNmEn());
            tvRepGenreNm.setText(vo.getRepGenreNm());
        }
    }
}