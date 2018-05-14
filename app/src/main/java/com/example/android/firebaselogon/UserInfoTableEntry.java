package com.example.android.firebaselogon;

public class UserInfoTableEntry {
    String userUniqueId;
    String userEmail;
    String userName;
    String userStatus;
    int userCurrentScore;

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public void setUserUniqueId(String userUniqueId) {
        this.userUniqueId = userUniqueId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserCurrentScore() {
        return userCurrentScore;
    }

    public void setUserCurrentScore(int userCurrentScore) {
        this.userCurrentScore = userCurrentScore;
    }
}
