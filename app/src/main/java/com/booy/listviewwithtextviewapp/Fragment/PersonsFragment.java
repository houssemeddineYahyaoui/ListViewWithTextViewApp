package com.booy.listviewwithtextviewapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.booy.listviewwithtextviewapp.Adapter.PersonListAdapter;
import com.booy.listviewwithtextviewapp.DataHelper.DBPersonHelper;
import com.booy.listviewwithtextviewapp.Entites.Person;
import com.booy.listviewwithtextviewapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by booy on 15/03/2017.
 */

public class PersonsFragment extends Fragment {

        List<Person> data= new ArrayList<Person>();
        public int p;
        ListView listview;
        View view;

        @Override
        public void onCreate(Bundle savedInstanceState) {
        DBPersonHelper.init(getContext());
        super.onCreate(savedInstanceState);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_person, container, false);

        listview=(ListView)view.findViewById(R.id.listview);
        refresh();




        return view;
    }

    public void refresh(){
        data=DBPersonHelper.getAllPersonData();
        PersonListAdapter personListAdapter = new PersonListAdapter(getContext(), (ArrayList<Person>) data);
        listview.setAdapter(personListAdapter);
    }



}

