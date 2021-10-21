package com.koreait.first.ch10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.koreait.first.R;
import com.koreait.first.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DailyBoxofficeActivity extends AppCompatActivity {

    private DailyBoxofficeAdapter adapter;

    private DatePicker dpTargetDt;//세트
    private RecyclerView rvList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_boxoffice);

        adapter = new DailyBoxofficeAdapter();

        dpTargetDt = findViewById(R.id.dpTargetDt);//세트
        rvList = findViewById(R.id.rvList);
        rvList.setAdapter(adapter);

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

//                    List<DailyBoxOfficeVO> list2 = vo.getBoxOfficeResult().getDailyBoxOfficeList();

                    Log.i("myLog", list.size() + "개");

                    adapter.setList(list);
                    adapter.notifyDataSetChanged();//바꿨으니까 알려줘!

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

class DailyBoxofficeAdapter extends RecyclerView.Adapter<DailyBoxofficeAdapter.MyViewHolder> {

    private List<DailyBoxOfficeVO> list;

    public void setList(List<DailyBoxOfficeVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//레이아웃 연결
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_daily_boxoffice, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DailyBoxOfficeVO vo = list.get(position);
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

        public void setItem(DailyBoxOfficeVO vo) {
            tvTitle.setText(vo.getMovieNm());
            String numberComma = Utils.getNumberComma(vo.getAudiCnt());
            tvAudienceCnt.setText(numberComma + "명");
        }

    }
}