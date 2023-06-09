package com.translator;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class LanguageEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo()
    public String tflName;

    @ColumnInfo()
    public String ttlName;

    @ColumnInfo()
    public String tflCode;

    @ColumnInfo()
    public String ttlCode;
    @ColumnInfo()
    public String input;

    @ColumnInfo()
    public String output;

    @ColumnInfo()
    public boolean isFavorite;

    public boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTflName() {
        return tflName;
    }

    public void setTflName(String tflName) {
        this.tflName = tflName;
    }

    public String getTtlName() {
        return ttlName;
    }

    public void setTtlName(String ttlName) {
        this.ttlName = ttlName;
    }

    public String getTflCode() {
        return tflCode;
    }

    public void setTflCode(String tflCode) {
        this.tflCode = tflCode;
    }

    public String getTtlCode() {
        return ttlCode;
    }

    public void setTtlCode(String ttlCode) {
        this.ttlCode = ttlCode;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public LanguageEntity(String tflName, String ttlName, String tflCode, String ttlCode, String input, String output) {
        this.tflName = tflName;
        this.ttlName = ttlName;
        this.tflCode = tflCode;
        this.ttlCode = ttlCode;
        this.input = input;
        this.output = output;
    }

    public LanguageEntity(String tflName, String ttlName, String tflCode, String ttlCode, String input, String output, boolean isFavorite) {
        this.tflName = tflName;
        this.ttlName = ttlName;
        this.tflCode = tflCode;
        this.ttlCode = ttlCode;
        this.input = input;
        this.output = output;
        this.isFavorite = isFavorite;
    }

    public LanguageEntity() {}
}
