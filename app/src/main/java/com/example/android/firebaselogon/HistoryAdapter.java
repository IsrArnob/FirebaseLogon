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

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.RecyclerViewHolder> {

    private ArrayList<DataProvider> arrayList = new ArrayList<DataProvider>();
    private Context ctx;

    public HistoryAdapter(ArrayList<DataProvider> arrayList, Context ctx) {
        /* Initialize constructor variables */
        this.arrayList = arrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout, parent, false);
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
        holder.teamANameView.setText( dataProvider.getTeamNameA() );
        holder.teamBNameView.setText( dataProvider.getTeamNameB() );
        holder.teamAScoreView.setText( dataProvider.getTeamAScore() );
        holder.teamBScoreView.setText( dataProvider.getTeamBScore() );
        holder.pointsEarnedView.setText( dataProvider.getPointsEarned() );
        holder.pointTypeView.setText( dataProvider.getPointType() );
        teamAScore[position] = Integer.valueOf( dataProvider.getTeamAScore() );
        teamBScore[position] = Integer.valueOf( dataProvider.getTeamBScore() );
        Log.v( "CHECK", "I'm there" );
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView teamANameView, teamBNameView, teamAScoreView, teamBScoreView, pointsEarnedView, pointTypeView;
        ArrayList<DataProvider> dataProvider = new ArrayList<DataProvider>(  );
        Context ctx;

        public RecyclerViewHolder(View view, Context ctx, ArrayList<DataProvider> dataProvider) {
            super( view );
            this.dataProvider = dataProvider;
            this.ctx = ctx;
            teamANameView = view.findViewById( R .id.history_teamA_name);
            teamBNameView = view.findViewById( R .id.history_teamB_name);
            teamAScoreView = view.findViewById( R.id.history_teamA_score);
            teamBScoreView = view.findViewById( R.id.history_teamB_score);
            pointsEarnedView = view.findViewById( R.id.history_match_point );
            pointTypeView = view.findViewById( R.id.history_match_point_type );
        }
    }
}
