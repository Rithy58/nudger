package com.idealessidealist.nudger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.idealessidealist.nudger.connector.GoogleConnector;
import com.idealessidealist.nudger.model.Group;
import com.idealessidealist.nudger.model.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Kenny Tsui on 10/8/2016.
 */

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.joinedList)
    ListView joinList;
    @BindView(R.id.pendingList)
    ListView pendingList;
    @BindView(R.id.tab_host)
    TabHost tabHost;

    TabHost.TabSpec joined;
    TabHost.TabSpec pending;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        tabHost.setup();
        joined = tabHost.newTabSpec("Joined");
        joined.setContent(R.id.joined);
        joined.setIndicator("Joined");
        tabHost.addTab(joined);

        pending = tabHost.newTabSpec("Pending");
        pending.setContent(R.id.pending);
        pending.setIndicator("Pending");
        tabHost.addTab(pending);

        tabHost.setOnTabChangedListener(tabId -> {
            if (!tabId.equals("Joined")) {
                pending.setIndicator("Pending");
            } else if (pendingList.getAdapter() != null) {
                pending.setIndicator("Pending (" + pendingList.getAdapter().getCount() + ")");
            }
        });
        tabHost.setOnLongClickListener(v -> {
            requestGroups();
            Toast.makeText(getApplicationContext(), "Refreshing...", Toast.LENGTH_SHORT).show();
            return true;
        });
        requestGroups();
    }

    private void requestGroups() {
        GoogleConnector.getGroup(UserService.getInstance().getUser().getEmail())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(groupListResponse -> {
            if (groupListResponse.code() == 200) {
                joinList.setAdapter(new ArrayAdapter<String>(this, R.layout.group_list_view, groupListResponse.body().getJoined()) {
                    @NonNull
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        String g = getItem(position);
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_list_view, parent, false);
                        }
                        // Lookup view for data population
                        TextView tvName = (TextView) convertView.findViewById(R.id.groupName);
                        TextView tvHome = (TextView) convertView.findViewById(R.id.remaining);
                        // Populate the data into the template view using the data object
                        tvName.setText(g);
//                        tvHome.setText(g.getRemaining());
                        // Return the completed view to render on screen
                        return convertView;
                    }
                });
                pending.setIndicator("Pending (" + groupListResponse.body().getPending().size() + ")");
                pendingList.setAdapter(new ArrayAdapter<String>(this, R.layout.group_list_view, groupListResponse.body().getPending()) {
                    @NonNull
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        String g = getItem(position);
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_list_view, parent, false);
                        }
                        // Lookup view for data population
                        TextView tvName = (TextView) convertView.findViewById(R.id.groupName);
                        TextView tvHome = (TextView) convertView.findViewById(R.id.remaining);
                        // Populate the data into the template view using the data object
                        tvName.setText(g);
//                        tvHome.setText(g.getRemaining());
                        // Return the completed view to render on screen
                        return convertView;
                    }
                });
            }
        });
    }

    @OnClick(R.id.create)
    public void createGroup() {
        startActivity(new Intent(getApplicationContext(), CreateActivity.class));
    }
}
