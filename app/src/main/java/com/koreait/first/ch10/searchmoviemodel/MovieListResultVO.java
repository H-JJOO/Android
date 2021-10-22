package com.koreait.first.ch10.searchmoviemodel;

import java.util.List;

public class MovieListResultVO {
    private int totCnt;
    private List<MovieVo> movieList;

    public int getTotCnt() {
        return totCnt;
    }

    public void setTotCnt(int totCnt) {
        this.totCnt = totCnt;
    }

    public List<MovieVo> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieVo> movieList) {
        this.movieList = movieList;
    }
}
