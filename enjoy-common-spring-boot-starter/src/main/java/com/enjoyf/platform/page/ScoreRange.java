package com.enjoyf.platform.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by zhimingli on 2017/5/12.
 */
@JsonIgnoreProperties(value = {"min", "max", "containStart", "containEnd", "limit"})
public class ScoreRange implements Serializable {

    private double min = -1.0;
    private double max = -1.0;
    private boolean isFirstPage;
    private double scoreflag;
    private ScoreSort sort = ScoreSort.DESC;
    private boolean hasnext;
    private int limit = 0;

    private int size = 10;

    public ScoreRange() {
    }

    public ScoreRange(double min, double max) {
        this(min, max, 10, ScoreSort.DESC);
    }

    public ScoreRange(double min, double max, int size) {
        this(min, max, size, ScoreSort.DESC);
    }

    public ScoreRange(double min, double max, int size, ScoreSort sort) {
        this.min = min;
        this.max = max;
        this.size = size;
        this.sort = sort;

        isFirstPage = sort.equals(ScoreSort.DESC) ? max < 0 : min < 0;
    }


    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getScoreflag() {
        return scoreflag;
    }

    public void setScoreflag(double scoreflag) {
        this.scoreflag = scoreflag;
    }

    public boolean isHasnext() {
        return hasnext;
    }

    public void setHasnext(boolean hasnext) {
        this.hasnext = hasnext;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public ScoreSort getSort() {
        return sort;
    }

    public void setSort(ScoreSort sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ScoreRange{" +
                "min=" + min +
                ", max=" + max +
                ", isFirstPage=" + isFirstPage +
                ", scoreflag=" + scoreflag +
                ", hasnext=" + hasnext +
                ", limit=" + limit +
                ", size=" + size +
                '}';
    }
}
