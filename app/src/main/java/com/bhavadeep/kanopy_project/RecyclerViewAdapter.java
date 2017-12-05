package com.bhavadeep.kanopy_project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhavadeep.kanopy_project.Models.*;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CommitViewHolder> {

    private Context context;
    private List<MasterCommit> commits;
    private ListItemClickListener listener;

    RecyclerViewAdapter(Context mContext, List<MasterCommit> commitList, ListItemClickListener clickListener) {
        context = mContext;
        commits = commitList;
        listener = clickListener;
    }

    @Override
    public CommitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_commits_recycler_view, parent, false);
        return new CommitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommitViewHolder holder, int position) {

       MasterCommit masterCommit = commits.get(position);

        String message = masterCommit.getCommit().parseMessage();
       holder.titleTextView.setText(message.substring(0,message.indexOf("\n") ));
       holder.authorTextView.setText(masterCommit.getCommit().getAuthor().getName());
       holder.dateTextView.setText(masterCommit.getCommit().getCommitter().getDateFormatted().toString());
    }

    @Override
    public int getItemCount() {
        return commits.size();
    }

    class CommitViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView authorTextView;
        TextView dateTextView;


        CommitViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.sha_tv);
            authorTextView = itemView.findViewById(R.id.author);
            dateTextView = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }

    interface ListItemClickListener{
        public void onItemClicked(int position);
    }

}
