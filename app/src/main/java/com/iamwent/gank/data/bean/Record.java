package com.iamwent.gank.data.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by iamwent on 25/02/2017.
 *
 * @author iamwent
 * @since 25/02/2017
 */

@Table("record")
public class Record {

    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("date")
    public String date;

    @Column("ganks")
    public List<Gank> ganks;

    public Record() {
    }

    public Record(String date) {
        this.date = date;
    }

    public Record(String date, List<Gank> ganks) {
        this.date = date;
        this.ganks = ganks;
    }

    public static List<Record> wrap(List<String> dates) {
        if (dates == null || dates.size() == 0) {
            return Collections.emptyList();
        }

        List<Record> records = new ArrayList<>(dates.size());
        for (String date : dates) {
            records.add(new Record(date));
        }

        return records;
    }
}
