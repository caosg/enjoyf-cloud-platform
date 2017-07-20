package com.enjoyf.platform.page;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 14/11/18
 * Description:
 */
public class ScoreRangeRows<T> implements Serializable {
    private ScoreRange range;
    private List<T> rows = new ArrayList<T>();
    private int totalRows = 0;
 
    public ScoreRangeRows() {
    }

    public ScoreRange getRange() {
        return range;
    }

    public void setRange(ScoreRange range) {
        this.range = range;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    @Override
    public String toString() {
        return "ScoreRangeRows{" +
                "range=" + range +
                ", rows=" + rows +
                '}';
    }
}
