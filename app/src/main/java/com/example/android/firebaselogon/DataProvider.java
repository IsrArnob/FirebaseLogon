package com.example.android.firebaselogon;

public class DataProvider {

    public DataProvider(String userUniqueId, String userEmail, String teamAScore, String teamBScore, String teamNameA, String teamNameB, String  pointsEarned, String pointType){
        this.setUserUniqueId( userUniqueId );
        this.setUserEmail( userEmail );
        this.setTeamNameA( teamNameA );
        this.setTeamNameB( teamNameB );
        this.setTeamAScore( teamAScore );
        this.setTeamBScore( teamBScore );
        this.setPointsEarned( pointsEarned );
    }

    private String userUniqueId, userEmail;
    String teamAScore, teamBScore;
    String teamNameA, teamNameB;
    String pointsEarned, pointType;

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

    public String getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(String teamAScore) {
        this.teamAScore = teamAScore;
    }

    public String getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(String teamBScore) {
        this.teamBScore = teamBScore;
    }

    public String getTeamNameA() {
        return teamNameA;
    }

    public void setTeamNameA(String teamNameA) {
        this.teamNameA = teamNameA;
    }

    public String getTeamNameB() {
        return teamNameB;
    }

    public void setTeamNameB(String teamNameB) {
        this.teamNameB = teamNameB;
    }

    public String getPointsEarned() { return pointsEarned; }

    public void setPointsEarned(String pointsEarned) { this.pointsEarned = pointsEarned; }

    public String getPointType() { return pointType; }

    public void setPointType(String pointType) { this.pointType = pointType; }
}
