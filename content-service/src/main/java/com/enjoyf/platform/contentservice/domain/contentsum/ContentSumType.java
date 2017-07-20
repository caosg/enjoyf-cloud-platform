package com.enjoyf.platform.contentservice.domain.contentsum;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 14/12/10
 * Description:
 */
public class ContentSumType implements Serializable {
    private static Map<String, ContentSumType> map = new HashMap<>();

    public static final ContentSumType AGREE_NUM = new ContentSumType("agree_num");    //点赞

    private String code;

    public ContentSumType(String code) {
        this.code = code;

        map.put(code, this);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ContentSumType: code=" + code;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;


        if (code != ((ContentSumType) o).code) return false;

        return true;
    }

    public static ContentSumType getByCode(int c) {
        return map.get(c);
    }

    public static Collection<ContentSumType> getAll() {
        return map.values();
    }
}
