package com.example.shareit;

public class dashboard_model {
    String st,font,uid,image_uri,user;
    int post_col,pen_col;

    public dashboard_model(){

    }

    public dashboard_model(String st, String font, int post_col, int pen_col,String uid,String image_uri,String user) {
        this.st = st;
        this.font = font;
        this.post_col = post_col;
        this.pen_col = pen_col;
        this.uid=uid;
        this.image_uri=image_uri;
        this.user=user;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public String getUser() {
        return user;
    }

    public String getSt() {
        return st;
    }

    public String getFont() {
        return font;
    }

    public int getPost_col() {
        return post_col;
    }

    public int getPen_col() {
        return pen_col;
    }

    public String getUid() {
        return uid;
    }

}
