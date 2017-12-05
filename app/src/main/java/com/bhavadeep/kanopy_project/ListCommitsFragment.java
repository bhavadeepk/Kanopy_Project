package com.bhavadeep.kanopy_project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bhavadeep.kanopy_project.Data.CommitEntity;
import com.bhavadeep.kanopy_project.Data.DaoAccess;
import com.bhavadeep.kanopy_project.Data.LocalDatabase;
import com.bhavadeep.kanopy_project.Models.MasterCommit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListCommitsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListCommitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListCommitsFragment extends Fragment implements RecyclerViewAdapter.ListItemClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_CODE = 1;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerViewAdapter adapter;
    List<MasterCommit> listCommits;
    ProgressBar progressBar;
    Context context;
    LocalDatabase database;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sort_by_date){
            adapter.sortByDate();
        }
        return super.onOptionsItemSelected(item);
    }

    private OnFragmentInteractionListener mListener;
    private boolean isListShowing;
    private static List<CommitEntity> commitEntityList;
    private Boolean isPopulating;

    public ListCommitsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListCommitsFragment.
     */
    public static ListCommitsFragment newInstance() {
        return new ListCommitsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //To save the state of the fragment on activity recreation
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_commits, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        listCommits = new ArrayList<>();
        commitEntityList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(getActivity(), commitEntityList, this);
         layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        isListShowing = false;
        database =LocalDatabase.getInstance(context);

        progressBar = rootView.findViewById(R.id.progressBar);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isListShowing) {
            progressBar.setIndeterminate(true);
        }

        //Check if internet permission is granted

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET) == PERMISSION_GRANTED) {
            //If internet is available get cloud data else look for local data
            if(isInternetAvailable()){
                retrieveCloudData();
            }
            else {
                retrieveLocalData();
            }

        }
        else{ // If device is marshmellow or above dynamically ask for internet permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, ListCommitsFragment.REQUEST_CODE);
            }
            else{
                Toast.makeText(getActivity(), "Please grant internet permission", Toast.LENGTH_SHORT).show();
                //Intent to navigate user to the application settings page to enable internet permission
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            this.context = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClicked(int position) {
        mListener.onCommitSelected(commitEntityList.get(position));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onCommitSelected(CommitEntity commit);
    }

    //Callback method to see if user has granted the internet permission requested
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults[0] != PERMISSION_GRANTED){
                if(  shouldShowRequestPermissionRationale(Manifest.permission.CAMERA )) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, ListCommitsFragment.REQUEST_CODE);
                }
                else
                {
                    //Intent to navigate user to the application settings page to enable internet permission
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        }
    }


    public void retrieveCloudData(){
        RetrofitClient.GitHub gitHubApi = RetrofitClient.getGitHUbService();

        Call<List<MasterCommit>> call = gitHubApi.listCommits();

        call.enqueue(new Callback<List<MasterCommit>>() {
            @Override
            public void onResponse(@NonNull Call<List<MasterCommit>> call, @NonNull Response<List<MasterCommit>> response) {

                if (progressBar.isShown()) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                listCommits.addAll(response.body());
                isPopulating = true;
                PopulateAsyncDB asyncDB = new PopulateAsyncDB(database);
                asyncDB.execute(isPopulating);
                isListShowing = true;
            }
            @Override
            public void onFailure(@NonNull Call<List<MasterCommit>> call, @NonNull Throwable t) {

                Toast.makeText(getActivity(), "Error Loading page", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //Retrieve data available in the local database
    public void retrieveLocalData(){
            PopulateAsyncDB asyncDB = new PopulateAsyncDB(database);
            isPopulating = false;
            asyncDB.execute(isPopulating);
    }

    //Check Live internet connection
    public boolean isInternetAvailable(){
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo() != null;

    }


    /**
     * Asynchronous class to handle database tasks
     */

    class PopulateAsyncDB extends AsyncTask<Boolean, Void, Void>{

        DaoAccess daoAccess;
        LocalDatabase localDatabase;

       PopulateAsyncDB(LocalDatabase db) {
           super();
           localDatabase = db;
       }

       CommitEntity extractCommits(MasterCommit masterCommit){
           CommitEntity commitEntity = new CommitEntity();
           commitEntity.setAuthor(masterCommit.getCommit().getAuthor().getName());
           commitEntity.setEmail(masterCommit.getCommit().getAuthor().getEmail());
           commitEntity.setDate(masterCommit.getCommit().getCommitter().getDateFormatted().toString());
           commitEntity.setMessage(masterCommit.getCommit().parseMessage());
           commitEntity.setSha(masterCommit.getSha());
           if(masterCommit.getAuthor()!= null){
               commitEntity.setImageUrl(masterCommit.getAuthor().getAvatarUrl());
               commitEntity.setProfileUrl(masterCommit.getAuthor().getHtmlUrl());
           }
           else {
               commitEntity.setImageUrl(null);
               commitEntity.setProfileUrl(null);
           }
           commitEntity.setHtmlUrl(masterCommit.getHtmlUrl());
           return commitEntity;
       }


       @Override
        protected Void doInBackground(Boolean... booleans) {
            daoAccess = localDatabase.getDaoAccess();
            //Get data from cloud and populate local database
            if(booleans[0]) {
                List<CommitEntity> commitEntities = new ArrayList<>();
                for (MasterCommit commit : listCommits) {
                    commitEntities.add(extractCommits(commit));
                }
                daoAccess.insertMutipleCommits(commitEntities);
            }
            commitEntityList.addAll(daoAccess.fetchAllCommits());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressBar.isShown()){
                progressBar.setVisibility(View.INVISIBLE);
            }
            adapter.notifyDataSetChanged();

        }
    }
}
