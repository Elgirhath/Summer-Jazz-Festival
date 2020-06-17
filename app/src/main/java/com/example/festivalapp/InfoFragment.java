package com.example.festivalapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InfoFragment extends Fragment {

    View v;
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter adapter;

    public InfoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v =  inflater.inflate(R.layout.fragment_info, container, false);

        expandableListView = v.findViewById(R.id.expandable_ListView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(getContext(), listGroup, listItem);
        expandableListView.setAdapter(adapter);
        initListData();
        return v;
    }

    private void initListData() {
        listGroup.add(getString(R.string.question1));
        listGroup.add(getString(R.string.question2));
        listGroup.add(getString(R.string.question3));
        listGroup.add(getString(R.string.question4));
        listGroup.add(getString(R.string.question5));
        listGroup.add(getString(R.string.question6));


        String[] array;

        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.question1);
        for (String item : array) {
            list1.add(item);
        }

        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.question2);
        for (String item : array) {
            list2.add(item);
        }

        List<String> list3 = new ArrayList<>();
        array = getResources().getStringArray(R.array.question3);
        for (String item : array) {
            list3.add(item);
        }

        List<String> list4 = new ArrayList<>();
        array = getResources().getStringArray(R.array.question4);
        for (String item : array) {
            list4.add(item);
        }

        List<String> list5 = new ArrayList<>();
        array = getResources().getStringArray(R.array.question5);
        for (String item : array) {
            list5.add(item);
        }

        List<String> list6 = new ArrayList<>();
        array = getResources().getStringArray(R.array.question6);
        for (String item : array) {
            list6.add(item);
        }

        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list3);
        listItem.put(listGroup.get(3),list4);
        listItem.put(listGroup.get(4),list5);
        listItem.put(listGroup.get(5),list6);
        adapter.notifyDataSetChanged();
    }

}
