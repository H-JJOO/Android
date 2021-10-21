package com.koreait.first.picsum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koreait.first.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PicsumActivity extends AppCompatActivity {

    private PicsumAdapter adapter;//어뎁터 전역변수 선언

    private RecyclerView rvList;//연결


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Android 플랫폼 상 약속 (java 문법상은 없어도된다.)
        setContentView(R.layout.activity_picsum);

        adapter = new PicsumAdapter();//어뎁터 객체화

        rvList = findViewById(R.id.rvList);//연결
        rvList.setAdapter(adapter);//rvList에 어뎁터 세팅

        network();//통신

    }

    private void network() {

        Retrofit rf = new Retrofit.Builder()//객체화
                .baseUrl("https://picsum.photos/")//사이트의 기본주소(와 통신하겠다.)
                .addConverterFactory(GsonConverterFactory.create())//json 형태의 문자열을 자바 문자열로 변환
                .build();

        //RetrofitService 인터페이스를 구현한 객체를 리턴해 준다.
        RetrofitService service = rf.create(RetrofitService.class);

        Call<List<PicsumVO>> call = service.getList();//제네릭해서 List<PicsumVO> 형태만 get 하겠다.

        //비동기처리
        call.enqueue(new Callback<List<PicsumVO>>() {

            //응답이 성공한 경우 실행될 내용작성(통신완료되었을때)
            @Override
            public void onResponse(Call<List<PicsumVO>> call, Response<List<PicsumVO>> response) {
                if (response.isSuccessful()) {

                    Log.i("myLog", "----- response 성공 -----");
                    List<PicsumVO> list = response.body();//data 는 여기에
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();

                } else {
                    Log.d("myLog", "response 실패");
                }
            }

            //응답이 실패한 경우 실행될 내용 작성(통신실패되었을때)
            @Override
            public void onFailure(Call<List<PicsumVO>> call, Throwable t) {
                Log.d("myLog", "통신 실패");
            }
        });
    }
}

class PicsumAdapter extends RecyclerView.Adapter<PicsumAdapter.PicsumViewHolder> {
    //PicsumVO 를 담고 있는 List
    private List<PicsumVO> list;

    //객체화는 body 가 해서 준다. 연결만 하면됨
    public void setList(List<PicsumVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PicsumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //이 부분 바꿔야 함 보라색 부분 (item_picsum)
        View v = inflater.inflate(R.layout.item_picsum, parent, false);
        //객체화도 바꾸는게 좋음
        return new PicsumViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PicsumViewHolder holder, int position) {
        PicsumVO vo = list.get(position);
        holder.setItem(vo);
    }

    @Override
    public int getItemCount() {
        //최초 list null 인상태에서 메소드 호출하면 에러뜸
        return list == null ? 0 : list.size();//몇개 가지고 있는지 리턴
    }

    static class PicsumViewHolder extends RecyclerView.ViewHolder {
        //연결할수있게끔 전역변수에
        private ImageView ivImg;
        private TextView tvAuthor;
        private View v;

        public PicsumViewHolder(View v) {
            super(v);
            this.v = v;
            //연결시켜줘야 findViewById 로 찾을수 있음
            ivImg = v.findViewById(R.id.ivImg);
            tvAuthor = v.findViewById(R.id.tvAuthor);
        }

        //자료넣기
        public void setItem(PicsumVO vo) {
            //로딩된 url
            //v : layout 넣어주고 vo.getDownload_url()
            Glide.with(v).load(vo.getDownload_url()).into(ivImg);
            tvAuthor.setText(vo.getAuthor());//author 작가 정보
        }


    }
}