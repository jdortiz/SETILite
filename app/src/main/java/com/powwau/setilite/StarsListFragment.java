package com.powwau.setilite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class StarsListFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {

    String [] mStarNames;
    Random mRandom;
    SignalDataAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public StarsListFragment() {
        mRandom = new Random();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stars_list, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareListView();
    }

    private void prepareListView() {
        mStarNames = getActivity().getResources().getStringArray(R.array.stars);
        List<SignalData> entries = new ArrayList<>();
        mAdapter = new SignalDataAdapter(getActivity(), entries);
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String starName = parent.getItemAtPosition(position).toString();
                String message = String.format(getString(R.string.message_received), starName);
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_stars_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Boolean handled = false;
        switch (id) {
            case R.id.action_add:
                addSignalData();
                handled = true;
                break;
        }
        if (!handled) {
            handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }

    private void addSignalData() {
        SignalData signalData = new SignalData(getActivity(), mStarNames[mRandom.nextInt(mStarNames.length)]);
        mAdapter.add(signalData);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        int newEntries = mRandom.nextInt(6);
        addEntries(newEntries);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void addEntries(int nEntries) {
        for(int i = 0; i < nEntries; i++) {
            addSignalData();
        }
        mAdapter.notifyDataSetChanged();
    }
}
