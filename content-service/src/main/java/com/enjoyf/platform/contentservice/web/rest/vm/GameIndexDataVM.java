package com.enjoyf.platform.contentservice.web.rest.vm;

import com.enjoyf.platform.page.ScoreRange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhimingli on 2017/6/27.
 */
public class GameIndexDataVM {

    private List<GameIndexVM> rows;  //整合的数据

    private ScoreRange gameScoreRange;   //游戏分页

    private ScoreRange profileScoreRange; //推荐用户分页

    private List<ContentVM> headItems=new ArrayList<>(); //轮播图

    public List<GameIndexVM> getRows() {
        return rows;
    }

    public void setRows(List<GameIndexVM> rows) {
        this.rows = rows;
    }

    public ScoreRange getGameScoreRange() {
        return gameScoreRange;
    }

    public void setGameScoreRange(ScoreRange gameScoreRange) {
        this.gameScoreRange = gameScoreRange;
    }

    public ScoreRange getProfileScoreRange() {
        return profileScoreRange;
    }

    public void setProfileScoreRange(ScoreRange profileScoreRange) {
        this.profileScoreRange = profileScoreRange;
    }

    public List<ContentVM> getHeadItems() {
        return headItems;
    }

    public void setHeadItems(List<ContentVM> headItems) {
        this.headItems = headItems;
    }
}
