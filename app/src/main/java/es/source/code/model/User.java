package es.source.code.model;

import java.io.Serializable;

public class User implements Serializable{
    private String userName,password;
    boolean oldUser;
    public User(String userName,String password,boolean oldUser){
        this.userName = userName;
        this.password = password;
        this.oldUser = oldUser;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOldUser(boolean isoldUser) {
        this.oldUser = isoldUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isOldUser() {
        return oldUser;
    }
}
