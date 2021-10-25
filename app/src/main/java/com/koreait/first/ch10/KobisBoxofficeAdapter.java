package com.koreait.first.ch10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koreait.first.R;
import com.koreait.first.Utils;
import com.koreait.first.ch10.boxofficemodel.BoxOfficeVO;

import java.util.List;

public class KobisBoxofficeAdapter extends RecyclerView.Adapter<KobisBoxofficeAdapter.MyViewHolder>  {
    private List<BoxOfficeVO> list;

    public void setList(List<BoxOfficeVO> list) {
        this.list = list;
    }//null 일 가능성 있음

    @NonNull
    @Override
    public KobisBoxofficeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//레이아웃 연결
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_daily_boxoffice, parent, false);
        return new KobisBoxofficeAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KobisBoxofficeAdapter.MyViewHolder holder, int position) {
        BoxOfficeVO vo = list.get(position);
        holder.setItem(vo);

        //holder.setItem(list.get(position));
    }

    @Override
    public int getItemCount() {
        //예외 발생하게 하지 않기 위해 최초 null 뜸
        return list == null ? 0 : list.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvAudienceCnt;

        public MyViewHolder(View v) {
            super(v);

            tvTitle = v.findViewById(R.id.tvTitle);
            tvAudienceCnt = v.findViewById(R.id.tvAudienceCnt);
        }

        public void setItem(BoxOfficeVO vo) {
            tvTitle.setText(vo.getMovieNm());
            String numberComma = Utils.getNumberComma(vo.getAudiCnt());
            tvAudienceCnt.setText(numberComma + "명");
        }

    }
}
