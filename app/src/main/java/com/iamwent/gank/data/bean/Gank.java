package com.iamwent.gank.data.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.util.List;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

@Table("gank")
public class Gank {

    /**
     * _id : 58ae49dd421aa957ef935322
     * createdAt : 2017-02-23T10:33:01.70Z
     * desc : App 商店截图生成工具，哈哈，这个需求满足的好，毕竟独立程序员都不会设计！
     * images : ["http://img.gank.io/95c6d36b-4a24-415c-8046-777fcb5da60d","http://img.gank.io/683b56a2-f2b3-4b5a-ad37-51da1b661561"]
     * publishedAt : 2017-02-23T11:50:23.116Z
     * source : chrome
     * type : iOS
     * url : https://github.com/6ag/AppScreenshots
     * used : true
     * who : 代码家
     */

    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("_id")
    public String _id;

    @Column("createdAt")
    public String createdAt;

    @Column("desc")
    public String desc;

    @Column("publishedAt")
    public String publishedAt;

    @Column("source")
    public String source;

    @Column("type")
    public String type;

    @Column("url")
    public String url;

    @Column("used")
    public boolean used;

    @Column("who")
    public String who;

    @Column("images")
    public List<String> images;
}
