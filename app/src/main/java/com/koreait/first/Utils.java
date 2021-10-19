package com.koreait.first;

import android.widget.Toast;

public class Utils {
    //문제가 발생되면 0 리턴
    //문제가 발생안되면 파싱한다.
    public static int parseStringToInt(String val) {

        // 에외처리(try catch 문)

        int intVal = 0;
        try {
            intVal = Integer.parseInt(val);//예외발생 할수도 있는 코드
        } catch (Exception e) {
            e.printStackTrace();//에러 내용 로그에 찍는다.
        } finally {
            //에러가 터지든 안터지든 !무조건! 실행되었으면 하는 것들을 작성.

        }

        return intVal;
    }
}
