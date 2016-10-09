package com.idealessidealist.nudger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by Kenny Tsui on 10/9/2016.
 */

public class ProfileFragment extends Fragment {
    public static Fragment newInstance(String user, String[] chores) {
        ProfileFragment f = new ProfileFragment();
        Bundle b = new Bundle();
        b.putStringArray("chores", chores);
        b.putString("user", user);
        f.setArguments(b);
        return f;
    }

    public static Fragment newInstance(String[] chores) {
        ProfileFragment f = new ProfileFragment();
        Bundle b = new Bundle();
        b.putStringArray("chores", chores);
        b.putString("user", "");
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chores = getArguments().getStringArray("chores");
        user = getArguments().getString("user");
    }

    private String[] chores;
    private String user;
    ListView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(view);
        list = ButterKnife.findById(view, R.id.choresList);
        TextView userEmail = ButterKnife.findById(view, R.id.userEmail);
        userEmail.setText(user);
        list.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, chores));
        return view;
    }

}
