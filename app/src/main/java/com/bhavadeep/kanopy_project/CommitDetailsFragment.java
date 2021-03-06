package com.bhavadeep.kanopy_project;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link CommitDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommitDetailsFragment extends Fragment {


    private static final String MESSAGE = "message";
    private static final String AUTHOR = "author";
    private static final String EMAIL = "email";
    private static final String DATE = "date";
    private static final String IMAGE_URL = "image_url";
    private static final String COMMIT_URL = "commit_url";
    private static final String PROFILE_URL = "profile_url";


    private String message;
    private String author;
    private String email;
    private String date;
    private String imageUrl;
    private String commitUrl;
    private String profileUrl;
    private boolean isImageLoaded;
    ProgressBar  progressBar;


    public CommitDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment CommitDetailsFragment.
     */
    public static CommitDetailsFragment newInstance(String message, String author, String email, String date, String imageUrl, String profileUrl, String commitUrl) {
        CommitDetailsFragment fragment = new CommitDetailsFragment();
       //Set arguments from the activity
        Bundle args = new Bundle();
        args.putString(MESSAGE, message);
        args.putString(AUTHOR, author);
        args.putString(EMAIL, email);
        args.putString(DATE, date);
        args.putString(IMAGE_URL, imageUrl);
        args.putString(PROFILE_URL, profileUrl);
        args.putString(COMMIT_URL, commitUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the arguments sent from the activity
        if (getArguments() != null) {
            message = getArguments().getString(MESSAGE);
            author = getArguments().getString(AUTHOR);
            email = getArguments().getString(EMAIL);
            date = getArguments().getString(DATE);
            imageUrl = getArguments().getString(IMAGE_URL);
            profileUrl =  getArguments().getString(PROFILE_URL);
            commitUrl = getArguments().getString(COMMIT_URL);
        }

        //To save the state of the fragment on activity recreation
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_commit_details, container, false);
        TextView messageTextView = rootView.findViewById(R.id.message_tv);
        TextView authorTextView = rootView.findViewById(R.id.author_tv);
        TextView emailTextView = rootView.findViewById(R.id.email_tv);
        TextView dateTextView = rootView.findViewById(R.id.date_tv);
        ImageView imageProfileView = rootView.findViewById(R.id.imageView);
        TextView commitTextView = rootView.findViewById(R.id.commit_tv);
        TextView messageTitleTextView = rootView.findViewById(R.id.message_title);
        TextView authorTitleTextView = rootView.findViewById(R.id.author_title);
        TextView emailTitleTextView = rootView.findViewById(R.id.email_title);
        TextView dateTitleTextView = rootView.findViewById(R.id.date_title);
        progressBar = rootView.findViewById(R.id.progressBar_2);

        // set headers for attributes
        messageTitleTextView.setText(R.string.message_title);
        authorTitleTextView.setText(R.string.author_title);
        emailTitleTextView.setText(R.string.email_title);
        dateTitleTextView.setText(R.string.date_title);
        isImageLoaded = false;

        messageTextView.setText(message);
        authorTextView.setText(author);
        emailTextView.setText(email);
        dateTextView.setText(date);

        // Check if user profile picture is available
        if(imageUrl != null) {
            //Using picaso to load online images
            if(isInternetAvailable()) {
                Picasso.with(getActivity()).load(imageUrl).centerCrop().fit().into(imageProfileView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                        isImageLoaded = true;
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
            else
                progressBar.setVisibility(View.INVISIBLE);
        }
        else {
            progressBar.setVisibility(View.INVISIBLE);
        }
        //Handle click on the user profile picture
        imageProfileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(profileUrl!= null) {
                        Intent profileIntent = new Intent(Intent.ACTION_VIEW);
                        profileIntent.setData(Uri.parse(profileUrl));
                        startActivity(profileIntent);
                    }
                    else {
                        Toast.makeText(getActivity(), "User Profile Unavailable", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        // Handle click on the full commit view request
        commitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commitIntent = new Intent(Intent.ACTION_VIEW);
                commitIntent.setData(Uri.parse(commitUrl));
                startActivity(commitIntent);
            }
        });

        messageTextView.setMovementMethod(new ScrollingMovementMethod());

        return rootView;
    }

    //Check Live internet connection
    public boolean isInternetAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;

    }
    @Override
    public void onResume() {
        super.onResume();
        if(!isImageLoaded)
            progressBar.setIndeterminate(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
