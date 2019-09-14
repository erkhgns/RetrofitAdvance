package com.erkhgns.retrofitadvance.Model;

import com.google.gson.annotations.SerializedName;

public class Post {

    private int userId;

    /**
     * Change to primitive because
     * we wont send id to constructor and it might throw a null pointer exception
     */
    private Integer id;

    private String title;

    /**
     * Serialize name is
     * the real variable name in JSON
     */
    @SerializedName("body")
    private String text;


    /**
     * Created a constructor for the POST function of Retrofit.
     *
     * the id variable is not included because the rest API will
     * auto generate that value ( Accdg to the tutorial)
     *
     *
     * @param userId
     * @param title
     * @param text
     */
    public Post(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
