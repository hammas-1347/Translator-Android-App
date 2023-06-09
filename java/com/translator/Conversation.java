package com.translator;

public class Conversation {
    public static final int left_chat_id = 0;
    public static   final int right_chat_id = 1;
    private String name;
    private String tfl;
    private String ttl;
    private String tfl_code;
    private String ttl_code;
    private int id;

    public Conversation(int id,String name, String tfl, String ttl, String tfl_code, String ttl_code) {
        this.name = name;
        this.tfl = tfl;
        this.ttl = ttl;
        this.tfl_code = tfl_code;
        this.ttl_code = ttl_code;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTfl() {
        return tfl;
    }

    public void setTfl(String tfl) {
        this.tfl = tfl;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTfl_code() {
        return tfl_code;
    }

    public void setTfl_code(String tfl_code) {
        this.tfl_code = tfl_code;
    }

    public String getTtl_code() {
        return ttl_code;
    }

    public void setTtl_code(String ttl_code) {
        this.ttl_code = ttl_code;
    }
}
