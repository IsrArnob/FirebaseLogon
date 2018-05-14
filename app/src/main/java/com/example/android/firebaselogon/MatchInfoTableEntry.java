package com.example.android.firebaselogon;

public class MatchInfoTableEntry {
    private String userUniqueId;
    private String matchId;
    private String userEmail;
    private String matchDate;
    private String matchType;
    private String teamAName;
    private String teamBName;
    private int teamAScore;
    private int teamBScore;
    private String userMatchUpdateStatus;
    private long scoreEarned;
    private String pointType;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

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

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public int getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(int teamAScore) {
        this.teamAScore = teamAScore;
    }

    public int getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(int teamBScore) {
        this.teamBScore = teamBScore;
    }

    public String getUserMatchUpdateStatus() {
        return userMatchUpdateStatus;
    }

    public void setUserMatchUpdateStatus(String userMatchUpdateStatus) {
        this.userMatchUpdateStatus = userMatchUpdateStatus;
    }

    public long getScoreEarned() {
        return scoreEarned;
    }

    public void setScoreEarned(long scoreEarned) {
        this.scoreEarned = scoreEarned;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }
}
