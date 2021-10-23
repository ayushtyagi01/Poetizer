package com.example.shareit;

public class Posts {
    public String st,font,uid,image_uri,user;
    public int post_col,pen_col;


    public Posts(){
    }
    public Posts(String st, int post_col, int pen_col,String font,String uid,String image_uri,String user) {
        this.st=st;
        this.post_col=post_col;
        this.pen_col=pen_col;
        this.font=font;
        this.uid=uid;
        this.image_uri=image_uri;
        this.user=user;
    }
}
