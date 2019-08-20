package com.example.rkg09.domesticservicesfreelancing.User;

/**
 * Created by rkg09 on 3/29/2019.
 */

import java.io.Serializable;

public class UserObject implements Serializable {

    private String  uid,
            name,
            phone,
            notificationKey;

    private Boolean selected = false;

    public UserObject(String uid){
        this.uid = uid;
    }
    public UserObject(String uid, String name, String phone){
        this.uid = uid;
        this.name = name;
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }
    public String getPhone() {
        return phone;
    }
    public String getName() {
        return name;
    }
    public String getNotificationKey() {
        return notificationKey;
    }
    public Boolean getSelected() {
        return selected;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setNotificationKey(String notificationKey) {
        this.notificationKey = notificationKey;
    }
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

}
