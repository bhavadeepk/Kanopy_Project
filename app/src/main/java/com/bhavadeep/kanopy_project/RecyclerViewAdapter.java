package com.bhavadeep.kanopy_project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhavadeep.kanopy_project.Data.CommitEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CommitViewHolder> {

    private Context context;
    private List<CommitEntity> commits;
    private ListItemClickListener listener;
    private boolean toggleDateSort = false;
    private boolean toggleAuthorSort = false;

    RecyclerViewAdapter(Context mContext, List<CommitEntity> commitList, ListItemClickListener clickListener) {
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

      //Binding data to each list item
       CommitEntity commitEntity = commits.get(position);
       String message = commitEntity.getMessage();
       //First line of message to display as title
       holder.titleTextView.setText(message.substring(0,message.indexOf("\n") ));
       holder.authorTextView.setText(commitEntity.getAuthor());
       holder.dateTextView.setText(commitEntity.getDate());
    }

    @Override
    public int getItemCount() {
        return commits.size();
    }

    //Sort by Date

    void sortByDate() {

        if(toggleDateSort) {
            Collections.sort(commits, new Comparator<CommitEntity>() {
                @Override
                public int compare(CommitEntity o1, CommitEntity o2) {

                    return (o1.changeToDateFormat().compareTo(o2.changeToDateFormat()));
                }
            });

        }
        else
        {
            Collections.sort(commits, new Comparator<CommitEntity>() {
                @Override
                public int compare(CommitEntity o1, CommitEntity o2) {
                    return (o2.changeToDateFormat()).compareTo(o1.changeToDateFormat());
                }
            });
        }
        toggleDateSort=!toggleDateSort;
        notifyDataSetChanged();
    }

    //Sort by Author Name
    void sortByName() {
        if(toggleAuthorSort) {
            toggleAuthorSort = false;
            Collections.sort(commits);
        }
        else {
            toggleAuthorSort = true;
            Collections.sort(commits);
            Collections.reverse(commits);
        }
        notifyDataSetChanged();
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

            //Handle click on each list item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }

    /**
     * Interface to establish communication between adapter and @ListCommitsFragment
     */
    interface ListItemClickListener{
        void onItemClicked(int position);
    }

}
