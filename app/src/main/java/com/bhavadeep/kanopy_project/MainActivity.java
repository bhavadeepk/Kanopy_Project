package com.bhavadeep.kanopy_project;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bhavadeep.kanopy_project.Models.Author;
import com.bhavadeep.kanopy_project.Models.AuthorGlobal;
import com.bhavadeep.kanopy_project.Models.Commit;
import com.bhavadeep.kanopy_project.Models.Committer;
import com.bhavadeep.kanopy_project.Models.MasterCommit;

public class MainActivity extends AppCompatActivity implements ListCommitsFragment.OnFragmentInteractionListener{

    private static final String TAG2 = "Details Fragment";
    private static final String TAG1 = "List Fragment";

    ListCommitsFragment listCommitsFragment;
    CommitDetailsFragment commitDetailsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCommitsFragment = ListCommitsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, listCommitsFragment, TAG1).commit();

        if(findViewById(R.id.fragment_container_2) != null){
            commitDetailsFragment = new CommitDetailsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_2, commitDetailsFragment, TAG2).addToBackStack(TAG2).commit();
        }

 }


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

        commitDetailsFragment = CommitDetailsFragment.newInstance(innerCommit.parseMessage(), innerAuthor.getName(),
                    innerAuthor.getEmail(), committer.getDateFormatted().toString(), avatarUrl , profileUrl, commit.getHtmlUrl());



        if (findViewById(R.id.fragment_container_2) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, commitDetailsFragment, TAG2).addToBackStack(TAG2).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_2, commitDetailsFragment, TAG2).addToBackStack(TAG2).commit();


        }
    }

}

