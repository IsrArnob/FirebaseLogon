package com.example.android.firebaselogon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<DataProvider> arrayList = new ArrayList<DataProvider>();
    private Context ctx;

    public RecyclerAdapter(ArrayList<DataProvider> arrayList, Context ctx) {
        /* Initialize constructor variables */
        this.arrayList = arrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder( view, ctx, arrayList );
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        final int pos = position;
        /* Initialize Integer array */
        final int[] teamAScore = new int[arrayList.size()],
                    teamBScore = new int[arrayList.size()];
        for (int i=0; i< arrayList.size(); i++){
            teamAScore [i] = 0;
            teamBScore [i] = 0;
        }
        DataProvider dataProvider = arrayList.get(position);
        holder.teamAView.setText( dataProvider.getTeamNameA() );
        holder.teamBView.setText( dataProvider.getTeamNameB() );
        holder.teamAScoreView.setText( dataProvider.getTeamAScore() );
        holder.teamBScoreView.setText( dataProvider.getTeamBScore() );
        teamAScore[position] = Integer.valueOf( dataProvider.getTeamAScore() );
        teamBScore[position] = Integer.valueOf( dataProvider.getTeamBScore() );
        Log.v( "CHECK", "I'm there" );
        /**
         *  Setup OnClickListener
         */
        /* Button Team A Up */
        holder.teamAUpButtonView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v( "INSIDEUPBUTTON", "Position: "+pos );
                Log.v( "INSIDEUPBUTTON", "score: "+teamAScore[pos] );
                DataProvider dataProvider = arrayList.get( pos );
                ++teamAScore[pos];
//                dataProvider.setTeamAScore( String.valueOf( teamAScore[pos]) );
                holder.teamAScoreView.setText( String.valueOf( teamAScore[pos]) );
            }
        } );
        /* Button Team A Down */
        holder.teamADownButtonView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v( "TEAMADOWN", "Position: " +pos );
                DataProvider dataProvider = arrayList.get( pos );
                if (teamAScore[pos] > 0){
                    --teamAScore[pos];
                    holder.teamAScoreView.setText( String.valueOf( teamAScore[pos]) );
                }
            }
        } );
        /* Button Team B Up */
        holder.teamBUpButtonView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v( "TEAMBUP", "Position: " +pos );
                DataProvider dataProvider = arrayList.get( pos );
                ++teamBScore[pos];
                holder.teamBScoreView.setText( String.valueOf( teamBScore[pos] ) );
            }
        } );
        /* Button Team B Down */
        holder.teamBDownButtonView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v( "TEAMBDOWN", "Position: " +pos );
                DataProvider dataProvider = arrayList.get( pos );
                if (teamBScore[pos] > 0){
                    --teamBScore[pos];
                    holder.teamBScoreView.setText( String.valueOf( teamBScore[pos] ) );
                }
            }
        } );
        /* Button Save */
        holder.saveButtonView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v( "SAVE", "Position : " +pos );
                DataProvider dataProvider = arrayList.get( pos );
                holder.teamAScoreView.setText( String.valueOf( teamAScore[pos] ) );
                holder.teamBScoreView.setText( String.valueOf( teamBScore[pos] ) );
                Log.v( "SAVE", "User mail: " +dataProvider.getUserEmail() );
                MatchInfoHandler matchInfoHandler = new MatchInfoHandler(
                        dataProvider.getUserUniqueId(), dataProvider.getUserEmail(),
                        "2018-06-26", "16:30:00",
                        dataProvider.getTeamNameA(), teamAScore[pos],
                        dataProvider.getTeamNameB(), teamBScore[pos], "A", 0, "Match"+pos);
//                Toast.makeText( ctx, "Score Saved", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView teamAView, teamBView, teamAScoreView, teamBScoreView;
        Button teamAUpButtonView, teamADownButtonView, teamBUpButtonView, teamBDownButtonView, saveButtonView;
//        int teamAScore [], teamBScore [];
        ArrayList<DataProvider> dataProvider = new ArrayList<DataProvider>(  );
        Context ctx;

        public RecyclerViewHolder(View view, Context ctx, ArrayList<DataProvider> dataProvider) {
            super( view );
            this.dataProvider = dataProvider;
            this.ctx = ctx;
            teamAView = view.findViewById( R .id.teamA_name);
            teamBView = view.findViewById( R .id.teamB_name);
            teamAScoreView = view.findViewById( R.id.teamA_score);
            teamBScoreView = view.findViewById( R.id.teamB_score);
            saveButtonView = view.findViewById( R.id.score_save );
            teamAUpButtonView = view.findViewById( R.id.teamA_increment );
            teamBUpButtonView = view.findViewById( R.id.teamB_increment );
            teamADownButtonView = view.findViewById( R.id.teamA_decrement );
            teamBDownButtonView = view.findViewById( R.id.teamB_decrement );
        }
    }
}
