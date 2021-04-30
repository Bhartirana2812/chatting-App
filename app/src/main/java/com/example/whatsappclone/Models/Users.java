package com.example.whatsappclone.Models;

public class Users {
    String Profilepic,mail,userName,password,userId,lastMessage;

    public Users(String profilepic, String mail, String userName, String password, String userId, String lastMessage) {
        this.Profilepic = profilepic;
        this.mail = mail;
        this.userName = userName;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public String getProfilepic() {
        return Profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.Profilepic = profilepic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Users(){

    }



       public Users(String mail, String userName, String password) {

        this.mail = mail;
        this.userName = userName;
        this.password = password;

    }



}
