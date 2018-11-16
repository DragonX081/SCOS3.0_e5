package es.source.code.model;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
    String userName = null;
    String password = null;
    public MyAuthenticator(){}
    public MyAuthenticator(String userName,String password){
        this.userName = userName;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName,password);
    }
}
