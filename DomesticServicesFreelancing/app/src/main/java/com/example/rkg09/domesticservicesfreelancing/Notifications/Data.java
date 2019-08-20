package com.example.rkg09.domesticservicesfreelancing.Notifications;

/**
 * Created by rkg09 on 4/26/2019.
 */

public class Data {
    private String user;
    private int icon;
    private String body;
    private String title;
    private String sented;

    public Data(String user, int icon, String body, String title, String sented){
        this.user = user;
        this.icon = icon;
        this.body = body;
        this.title = title;
        this.sented = sented;
    }
    public  Data(){}
    public void setUser(String user) {
        this.user = user;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setSented(String sented) {
        this.sented = sented;
    }
    public String getUser() {
        return user;
    }
    public int getIcon() {
        return icon;
    }
    public String getBody() {
        return body;
    }
    public String getTitle() {
        return title;
    }
    public String getSented() {
        return sented;
    }
}
