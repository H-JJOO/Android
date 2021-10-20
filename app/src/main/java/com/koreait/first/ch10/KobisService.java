package com.koreait.first.ch10;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KobisService {
    //일별 박스오피스
    @GET("boxoffice/searchDailyBoxOfficeList.json")
    Call<BoxOfficeResultBodyVO> boxofficeSearchDailyBoxOfficeList
            (@Query("key")String key, @Query("targetDt") String targetDt);
    //https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=de390418f9162804bdcd0fe1cffa546e&targetDt=20211019 요렇게 만들어 줌
}
