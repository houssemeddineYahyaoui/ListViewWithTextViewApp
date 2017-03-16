package com.booy.listviewwithtextviewapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.booy.listviewwithtextviewapp.DataHelper.DBPersonHelper;
import com.booy.listviewwithtextviewapp.Entites.Person;
import com.booy.listviewwithtextviewapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by booy on 15/03/2017.
 */

public class AddPersonFragment extends Fragment {
    private View view;
    private EditText editTextName,editTextAge,editTextNbPart;

    private Person person;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DBPersonHelper.init(getContext());
        view= inflater.inflate(R.layout.add_person_fragment, container, false);

        editTextName=(EditText)view.findViewById(R.id.editTextName);
        editTextAge=(EditText)view.findViewById(R.id.editTextAge);
        editTextNbPart=(EditText)view.findViewById(R.id.editTextNbPart);
        person=new Person();
        Button buttonValider=(Button) view.findViewById(R.id.buttonValider);
        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setName(editTextName.getText().toString());
                person.setAge(Integer.parseInt(editTextAge.getText().toString().trim()));
                person.setNbParticipations(Integer.parseInt(editTextNbPart.getText().toString().trim()));
                DBPersonHelper.addPersonData(person);


                Fragment fragment = new PersonsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container1, fragment)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();

    }





}
