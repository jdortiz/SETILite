package com.powwau.setilite;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new StarsListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class StarsListFragment extends Fragment {

        String [] mStarNames;
        Random mRandom;
        SignalDataAdapter mAdapter;

        public StarsListFragment() {
            mRandom = new Random();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_stars_list, container, false);
            prepareListView(rootView);
            return rootView;
        }

        private void prepareListView(View rootView) {
            mStarNames = getActivity().getResources().getStringArray(R.array.stars);
            List<SignalData> entries = new ArrayList<>();
            mAdapter = new SignalDataAdapter(getActivity(), entries);
            ListView listView = (ListView)rootView.findViewById(R.id.listview);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    }
}
