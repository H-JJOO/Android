package com.koreait.first.ch10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koreait.first.R;
import com.koreait.first.ch10.searchmoviemodel.MovieVo;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);


    }
}

class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {
    private List<MovieVo> list = new ArrayList();

    public void addItem(MovieVo vo) {
        list.add(vo);//쌓이게 하기 위해서
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_movie, parent, false);
        return new MovieListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieVo vo = list.get(position);
        holder.setItem(vo);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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

        public void setItem(MovieVo vo) {
            tvMovieNm.setText(vo.getMovieNm());
            tvRepNationNm.setText(vo.getRepNationNm());
            tvMovieNmEn.setText(vo.getMovieNmEn());
            tvRepGenreNm.setText(vo.getRepGenreNm());
        }
    }
}