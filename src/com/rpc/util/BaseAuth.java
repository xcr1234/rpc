package com.rpc.util;

import com.rpc.io.meta.Auth;

public class BaseAuth implements Auth {
    private static final long serialVersionUID = -338406087932085915L;

    public BaseAuth() {
        this.timeStamp = System.currentTimeMillis();
    }


    public BaseAuth(String username, String password) {
        this.username = username;
        this.password = password;
        this.timeStamp = System.currentTimeMillis();
    }

    private String username;
    private String password;
    private long timeStamp;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public long getTimeStamp() {
        return timeStamp;
    }
}
