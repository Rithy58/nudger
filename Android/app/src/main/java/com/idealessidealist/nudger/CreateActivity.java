package com.idealessidealist.nudger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.idealessidealist.nudger.connector.GoogleConnector;
import com.idealessidealist.nudger.model.UserService;
import com.idealessidealist.nudger.model.Validator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Kenny Tsui on 10/9/2016.
 */
public class CreateActivity extends AppCompatActivity {

    @BindView(R.id.groupName)
    TextInputEditText groupName;
    @BindView(R.id.inviter)
    TextInputEditText inviter;
    @BindView(R.id.inviteList)
    ListView inviteList;
    @BindView(R.id.unfocus)
    FrameLayout unfocus;

    private ArrayList<String> emailList;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);
        unfocus.requestFocus();
        emailList = new ArrayList<>();
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emailList);
        inviteList.setAdapter(adapter);

    }

    private Toast emailError;

    @OnEditorAction(R.id.inviter)
    public boolean addEmail(TextView input) {
        String email = input.getText().toString();
        if (Validator.isValidEmail(email)) {
            emailList.add(email);
            adapter.notifyDataSetChanged();
            inviter.setText("");
        } else {
            if (emailError != null) {
                emailError.cancel();
            }
            emailError = Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT);
            emailError.show();
        }
        return true;
    }

    @BindView(R.id.completeCreate)
    Button completeCreate;

    @OnClick(R.id.completeCreate)
    public void createGroup() {
        groupName.clearFocus();
        inviter.clearFocus();
        completeCreate.setEnabled(false);
        completeCreate.setSelected(true);
        GoogleConnector.createGroup(UserService.getInstance().getUser().getEmail(), groupName.getText().toString(), emailList)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(groupResponse -> {
            if (groupResponse.code() == 200) {
                Intent i = new Intent(this, GroupActivity.class);
                i.putExtra(GroupActivity.MEMBERS_EXTRA, groupResponse.body().getMembers().toArray(new String[groupResponse.body().getMembers().size()]));
                i.putExtra(GroupActivity.CHORES_EXTRA, groupResponse.body().getChores().toArray(new String[groupResponse.body().getChores().size()]));
                i.putExtra(GroupActivity.NAME_EXTRA, groupResponse.body().getName());
                startActivity(i);
            }
            completeCreate.setEnabled(true);
            completeCreate.setSelected(false);
        });
    }
}
