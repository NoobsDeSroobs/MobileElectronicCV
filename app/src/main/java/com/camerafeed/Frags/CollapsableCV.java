package com.example.imerso.camerafeed.Frags;


import android.app.ExpandableListActivity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.imerso.camerafeed.ExpandableListAdapter;
import com.example.imerso.camerafeed.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class CollapsableCV extends Fragment implements AdapterView.OnItemClickListener {
    ExpandableListAdapter listAdapter;
    ExpandableListView list;
    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collapsable_cv, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        list = (ExpandableListView)getView().findViewById(R.id.CVList);
        listAdapter = new ExpandableListAdapter(getView().getContext(), listDataHeader, listDataChild);

        // setting list adapter
        list.setAdapter(listAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        //setListAdapter(adapter);
        //getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // preparing list data
        prepareListData();


    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        String[] headers = getResources().getStringArray(R.array.CVFields);
        listDataHeader = new ArrayList<String>(Arrays.asList(headers));

        // Adding child data
        String[][] childFields = new String[listDataHeader.size()][];
        childFields[0] = getResources().getStringArray(R.array.BasicInfo);
        childFields[1] = getResources().getStringArray(R.array.PersonalInfo);
        childFields[2] = getResources().getStringArray(R.array.Achievements);
        childFields[3] = getResources().getStringArray(R.array.Education);
        childFields[4] = getResources().getStringArray(R.array.WorkExperience);
        childFields[5] = getResources().getStringArray(R.array.Skills);
        childFields[6] = getResources().getStringArray(R.array.Languages);
        childFields[7] = getResources().getStringArray(R.array.Interests);
        for (int i = 0; i < listDataHeader.size(); i++) {
            listDataChild.put(listDataHeader.get(i), Arrays.asList(childFields[i])); // Header, Child data
        }
    }


}
