package com.example.android.firebaselogon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public String[] teamAName, teamBName, teamAScore, teamBScore;
    String userEmail, userUniqueId;
    long pointsEarned;
    Context ctx;
    //ArrayList<DataProvider> arrayList = new ArrayList<DataProvider>(  );

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( HistoryActivity.this, LogoutActivity.class );
        startActivity( intent );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_history );

        Intent prevScreen = getIntent();
        userEmail = prevScreen.getStringExtra( "EXTRA_EMAIL" );
        userUniqueId = prevScreen.getStringExtra( "EXTRA_USERUNIQUE" );

        recyclerView = findViewById( R.id.history_recycler_view );
//        teamAName = getResources().getStringArray( R.array.team_a_name );
//        teamBName = getResources().getStringArray( R.array.team_b_name );
        final ArrayList<DataProvider> arrayList = new ArrayList<DataProvider>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tableReference = database.getReference();
        DatabaseReference tableChild = tableReference.child( "matchInfo" ).child( userUniqueId );

        tableChild.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                RecyclerView.Adapter adapter;
                RecyclerView.LayoutManager layoutManager;
                String key = dataSnapshot.getKey();
                Log.v( "KEYCHECK", "Key taken: " + key );
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    /**
                     * Refer to the youtube video
                     * https://www.youtube.com/watch?v=jEmq1B1gveM
                     */
                    Log.v( "LOOP", "Inside snapshot, serial " + i );
                    Log.v( "SNAPSHOT", "Snapshot: " + dataSnapshot );
//                    Log.v( "CHILD","Child key value is: "+dataSnapshot.child( "Match"+i ).getKey());
//                    Log.v( "CHILD","Child     value is: "+dataSnapshot.child( "Match"+i ).getValue());
                    /* Verify whether child is found with the appropriate Match ID */
                    MatchInfoTableEntry matchInfo = dataSnapshot.getValue( MatchInfoTableEntry.class );
                    Log.v( "FETCH TEST", "match Info: " + matchInfo );
                    Log.v( "FETCH", "Team A " + matchInfo.getTeamAName() + ", Score " + matchInfo.getTeamAScore() + ", serial: " + i );
                    Log.v( "FETCH", "Team B " + matchInfo.getTeamBName() + ", Score " + matchInfo.getTeamBScore() + ", serial: " + i );
                    DataProvider dataProvider = new DataProvider( userUniqueId, userEmail,
                            String.valueOf( matchInfo.getTeamAScore() ),
                            String.valueOf( matchInfo.getTeamBScore() ),
                            teamAName[i], teamBName[i],
                            String.valueOf( matchInfo.getScoreEarned() ), matchInfo.getPointType() );
                    arrayList.add( dataProvider );
                    //ELSE PART
//                        DataProvider dataProvider = new DataProvider( userUniqueId, userEmail, "0", "0", teamAName[i], teamBName[i] );
//                        arrayList.add( dataProvider );
//                        Log.v( "FETCH", "Score " + 0 + ", serial: " + i );
//                        Log.v( "FETCH", "Score " + 0 + ", serial: " + i );

                    i++;
                }
                adapter = new RecyclerAdapter( arrayList, ctx );
                recyclerView.setHasFixedSize( true );
                layoutManager = new LinearLayoutManager( ctx );
                recyclerView.setLayoutManager( layoutManager );
                recyclerView.setAdapter( adapter );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v( "LOOP", "Inside on cancelled" );
            }
        } );
    }
}
