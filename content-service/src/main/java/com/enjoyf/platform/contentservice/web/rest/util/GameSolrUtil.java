package com.enjoyf.platform.contentservice.web.rest.util;

import com.enjoyf.platform.contentservice.config.WebAppConfig;
import com.enjoyf.platform.contentservice.domain.FiledValue;
import com.enjoyf.platform.contentservice.domain.Game;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhimingli on 2017/6/29.
 */
@Component
public class GameSolrUtil {
    private static final String SEARCH_CORE_CONTENTSERVICEGAME = "contentservice-game";
    private static final String SEARCH_KEY_PARAM_NAME = "name";
    private static final String SEARCH_KEY_PARAM_ALIASNAME = "aliasName";
    private static final String SEARCH_KEY_PARAM_ENGLISHNAME = "englishName";
    private static final String SEARCH_KEY_PARAM_ID = "id";
    private static final String SEARCH_KEY_PARAM_CREATETIME = "createtime";

    private final static LocalDate date = LocalDate.now();

    @Autowired
    private WebAppConfig webAppConfig;

    @Autowired
    private RestUtil restUtil;


    public Page<FiledValue> searchGame(String queryString, Pageable pageable) {
        Page<FiledValue> pageRows = new PageImpl<FiledValue>(new ArrayList<>(), pageable, 0);
        if (StringUtils.isEmpty(queryString)) {
            return pageRows;
        }

        try {
            //(name aliasName englishName:english)
            StringBuffer str = new StringBuffer("(name aliasName englishName:");
            str.append(queryString);
            str.append(")");

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("c", SEARCH_CORE_CONTENTSERVICEGAME);
            paramMap.put("q", str.toString());
            paramMap.put("p", pageable.getPageNumber() + 1);
            paramMap.put("ps", pageable.getPageSize());
            String responseBody = restUtil.query(webAppConfig.getSolr_query() + "?c={c}&q={q}&p={p}&ps={ps}", paramMap);
            JSONObject jsonObject = new JSONObject().fromObject(responseBody);
            //返回值是错误
            if (jsonObject.getInt("rs") != 1) {
                return pageRows;
            }
            JSONObject pageObject = jsonObject.getJSONObject("page");
            int total = pageObject.getInt("total");
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            //返回值为空
            if (jsonArray == null) {
                return pageRows;
            }
            Object[] resultObject = jsonArray.toArray();
            List<FiledValue> returnList = new ArrayList<FiledValue>();
            for (Object object : resultObject) {
                JSONObject jsonobj = (JSONObject) object;
                try {
                    FiledValue filedValue = new FiledValue(jsonobj.getString(SEARCH_KEY_PARAM_ID), jsonobj.getString(SEARCH_KEY_PARAM_CREATETIME));
                    returnList.add(filedValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pageRows = new PageImpl<FiledValue>(returnList, pageable, total);
            return pageRows;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageRows;
    }

    public boolean saveGame(Game game) {
        try {

            solrGame search = new solrGame();
            search.setId(String.valueOf(game.getId()));
            search.setEntryid(String.valueOf(game.getId()));
            search.setName(game.getName());
            search.setAliasName(StringUtils.isEmpty(game.getAliasName()) ? "" : game.getAliasName());
            search.setEnglishName(StringUtils.isEmpty(game.getEnglishName()) ? "" : game.getEnglishName());

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("c", SEARCH_CORE_CONTENTSERVICEGAME);
            paramMap.put("field", new Gson().toJson(search));
            String responseBody = restUtil.query(webAppConfig.getSolr_save() + "?c={c}&field={field}", paramMap);
            JSONObject jsonObject = new JSONObject().fromObject(responseBody);
            return jsonObject.getInt("rs") == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteGame(Game game) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("(entryid:" + game.getId() + ")");

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("c", SEARCH_CORE_CONTENTSERVICEGAME);
            paramMap.put("q", sb.toString());
            String responseBody = restUtil.query(webAppConfig.getSolr_delete() + "?c={c}&q={q}", paramMap);
            JSONObject jsonObject = new JSONObject().fromObject(responseBody);
            return jsonObject.getInt("rs") == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void updateGameDB(Long id, Double score, Integer scoresum) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gameid", id);
        paramMap.put("score", score);
        paramMap.put("scoresum", scoresum);
        restUtil.query(webAppConfig.getUrl_wikiser() + "/api/wiki/game/updateGamedbByComment?gameid={gameid}&score={score}&scoresum={scoresum}", paramMap);
    }

    private class solrGame {
        private String entryid;//唯一(id+type)
        private String id;
        private String name;
        private String aliasName = "";
        private String englishName = "";
        private long createtime;

        public String getEntryid() {
            return entryid;
        }

        public void setEntryid(String entryid) {
            this.entryid = entryid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAliasName() {
            return aliasName;
        }

        public void setAliasName(String aliasName) {
            this.aliasName = aliasName;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }
    }

}



