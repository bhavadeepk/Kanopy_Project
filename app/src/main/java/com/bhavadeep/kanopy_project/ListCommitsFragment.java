package com.bhavadeep.kanopy_project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bhavadeep.kanopy_project.Models.MasterCommit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListCommitsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListCommitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListCommitsFragment extends Fragment implements RecyclerViewAdapter.ListItemClickListener {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<MasterCommit> listCommits;
    ProgressBar progressBar;
    Context context;

    private OnFragmentInteractionListener mListener;
    private boolean isListShowing;

    public ListCommitsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListCommitsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListCommitsFragment newInstance() {
        return new ListCommitsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_commits, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        listCommits = new ArrayList<>();

        adapter = new RecyclerViewAdapter(getActivity(), listCommits, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        isListShowing = false;

        progressBar = rootView.findViewById(R.id.progressBar);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isListShowing) {
            progressBar.setIndeterminate(true);
        }
        RetrofitClient.GitHub gitHubApi = RetrofitClient.getGitHUbService();

        Call<List<MasterCommit>> call = gitHubApi.listCommits();

        call.enqueue(new Callback<List<MasterCommit>>() {
            @Override
            public void onResponse(@NonNull Call<List<MasterCommit>> call, @NonNull Response<List<MasterCommit>> response) {

                if(progressBar.isShown()){
                    progressBar.setVisibility(View.INVISIBLE);
                }
                listCommits.addAll(response.body());
                adapter.notifyDataSetChanged();
                isListShowing = true;

            }

            @Override
            public void onFailure(@NonNull Call<List<MasterCommit>> call, @NonNull Throwable t) {

                Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });

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
        mListener.onCommitSelected(listCommits.get(position));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCommitSelected(MasterCommit commit);
    }
}
