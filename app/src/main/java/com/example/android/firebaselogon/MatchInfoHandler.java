package com.example.android.firebaselogon;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchInfoHandler {
    public MatchInfoHandler(String userUniqueId, String userEmail,
                            String matchDate, String matchType,
                            String teamAName, int teamAScore,
                            String teamBName, int teamBScore,
                            String userMatchUpdateStatus, long scoreEarned,
                            String matchId) {

        MatchInfoTableEntry oneMatchEntry = new MatchInfoTableEntry();
//        oneMatchEntry.setUserUniqueId( userUniqueId );
        oneMatchEntry.setUserEmail( userEmail );
        oneMatchEntry.setMatchDate( matchDate );
        oneMatchEntry.setMatchType( matchType );
        oneMatchEntry.setTeamAName( teamAName );
        oneMatchEntry.setTeamAScore( teamAScore );
        oneMatchEntry.setTeamBName( teamBName );
        oneMatchEntry.setTeamBScore( teamBScore );
        oneMatchEntry.setUserMatchUpdateStatus( userMatchUpdateStatus );
        oneMatchEntry.setScoreEarned( scoreEarned );

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference tableReference = database.getReference();
            DatabaseReference tableChild = tableReference.child( "matchInfo" );
            tableChild.child( userUniqueId ).child( matchId ).setValue( oneMatchEntry );
//            tableReference.child( "matchInfo" ).push().setValue( oneMatchEntry );
        }catch (Exception e){
            //**//
        }

    }
}
