package com.bhavadeep.kanopy_project;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bhavadeep.kanopy_project.Models.Author;
import com.bhavadeep.kanopy_project.Models.AuthorGlobal;
import com.bhavadeep.kanopy_project.Models.Commit;
import com.bhavadeep.kanopy_project.Models.Committer;
import com.bhavadeep.kanopy_project.Models.MasterCommit;

public class MainActivity extends AppCompatActivity implements ListCommitsFragment.OnFragmentInteractionListener{

    //Tags to uniquely identify each fragment
    private static final String TAG2 = "Details Fragment";
    private static final String TAG1 = "List Fragment";

    ListCommitsFragment listCommitsFragment;
    CommitDetailsFragment commitDetailsFragment;

    /**
     * Use this factory method to initialize all variable and set content view
     *
     * @param savedInstanceState Save values to persist data on orientation change
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Avoid unnecessary recreation of fragments on orientation change
        if(savedInstanceState != null){
            listCommitsFragment = (ListCommitsFragment)getSupportFragmentManager().findFragmentByTag(TAG1);
            commitDetailsFragment = (CommitDetailsFragment)getSupportFragmentManager().findFragmentByTag(TAG2);
            return;
        }
        else {

            //List Fragment attached to activity
            listCommitsFragment = ListCommitsFragment.newInstance();
            if (findViewById(R.id.fragment_container) != null) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, listCommitsFragment, TAG1).commit();
            }

            //Details Fragment attached in case of multi-pane
            if (findViewById(R.id.fragment_container_2) != null) {
                commitDetailsFragment = new CommitDetailsFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_2, commitDetailsFragment, TAG2).addToBackStack(TAG2).commit();
            }
        }

 }

    /**
     * Callback method when a commit from the list is selected
     * @param commit Commit selcted by the user
     */
    @Override
    public void onCommitSelected(MasterCommit commit) {
        Commit innerCommit = commit.getCommit();
        AuthorGlobal author = commit.getAuthor();
        Author innerAuthor = innerCommit.getAuthor();
        Committer committer = innerCommit.getCommitter();
        String avatarUrl = null;
        String profileUrl = null;
        if(author!=null) {
           avatarUrl = author.getAvatarUrl();
           profileUrl = author.getHtmlUrl();
        }

        //new instance of commit details fragment related to commit selected
        commitDetailsFragment = CommitDetailsFragment.newInstance(innerCommit.parseMessage(), innerAuthor.getName(),
                    innerAuthor.getEmail(), committer.getDateFormatted().toString(), avatarUrl , profileUrl, commit.getHtmlUrl());


        // Checking if the device is on multi-pane support mode
        if (findViewById(R.id.fragment_container_2) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, commitDetailsFragment, TAG2).addToBackStack(TAG2).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_2, commitDetailsFragment, TAG2).addToBackStack(TAG2).commit();


        }
    }

}

