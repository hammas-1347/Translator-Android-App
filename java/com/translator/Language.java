package com.translator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Language implements Serializable {


    private String language_name;
    private String language_code;
    private String language_code_for_tts;
    private String language_code_for_recognizer;

    public Language() {
    }

    public Language(String language_code,String language_name, String language_code_for_tts, String language_code_for_recognizer) {
        this.language_name = language_name;
        this.language_code = language_code;
        this.language_code_for_tts = language_code_for_tts;
        this.language_code_for_recognizer = language_code_for_recognizer;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getLanguage_code_for_tts() {
        return language_code_for_tts;
    }

    public void setLanguage_code_for_tts(String language_code_for_tts) {
        this.language_code_for_tts = language_code_for_tts;
    }

    public String getLanguage_code_for_recognizer() {
        return language_code_for_recognizer;
    }

    public void setLanguage_code_for_recognizer(String language_code_for_recognizer) {
        this.language_code_for_recognizer = language_code_for_recognizer;
    }
}
