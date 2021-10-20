package com.koreait.first.picsum;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {

    @GET("/v2/list")//만든주소값도
    Call<List<PicsumVO>> getList();//getList() 이거 바뀔수있음 Call 빼고 다 바뀔수 있음
    //비동기 방식, Call 인터페이스가 있으면 Android 가 알아서 해줌, <> 안에는 받고자하는 타입적으면 된다. PicsumVo 여러개가 리스트로 날아온다.

}
