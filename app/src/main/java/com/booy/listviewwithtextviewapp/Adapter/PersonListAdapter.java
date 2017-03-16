package com.booy.listviewwithtextviewapp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;


import com.booy.listviewwithtextviewapp.DataHelper.DBPersonHelper;
import com.booy.listviewwithtextviewapp.Entites.Person;
import com.booy.listviewwithtextviewapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by booy on 15/03/2017.
 */

public class PersonListAdapter extends BaseAdapter {

    public List<Person> data;



    private final Context context;


    LayoutInflater inflter;

    public PersonListAdapter(Context context, ArrayList<Person> data) {
        this.context = context;
        this.data = data;
        inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Person getItem(int position) {
        Person pp=new Person();
        int i=0;
        for(Person p:data)
            if(i==position){
                pp=p;
            }
        return pp;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //ViewHolder holder = null;
        final ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.person_item, null);

            holder.textViewID = (TextView) convertView.findViewById(R.id.textViewId);
            holder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);
            holder.textViewAge = (TextView) convertView.findViewById(R.id.textViewAge);
            holder.editTextNbPart = (EditText) convertView.findViewById(R.id.editTextNbPart);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.ref = position;
        holder.textViewID.setText(data.get(position).getId()+"");
        holder.textViewName.setText(data.get(position).getName()+"");
        holder.textViewAge.setText(data.get(position).getAge()+"");
        holder.editTextNbPart.setText(data.get(position).getNbParticipations()+"");

        holder.editTextNbPart.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                // arrTemp[holder.ref] = arg0.toString();



                holder.editTextNbPart.setOnKeyListener(new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        // If the event is a key-down event on the "enter" button
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {

                            int position = holder.ref;
                            int i=0;
                            for(Person p:data){
                                if(i==position){

                                    p.setNbParticipations(Integer.parseInt(holder.editTextNbPart.getText().toString().trim()));

                                    DBPersonHelper.updatePersonData(p);
                                }
                                i++;
                            }


                            return true;
                        }
                        return false;
                    }
                });
            }
        });

        return convertView;
    }

    private class ViewHolder {
        protected  TextView textViewID;
        protected  TextView textViewName;
        protected  TextView textViewAge;
        protected  EditText editTextNbPart;
        protected int ref;
    }


}

