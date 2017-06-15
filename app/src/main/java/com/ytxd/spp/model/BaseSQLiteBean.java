package com.ytxd.spp.model;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by XY on 2017/1/15.
 */

public class BaseSQLiteBean implements Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
