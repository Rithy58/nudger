package com.idealessidealist.nudger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.idealessidealist.nudger.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kenny Tsui on 10/9/2016.
 */

public class GroupActivity extends AppCompatActivity {

    public static final String MEMBERS_EXTRA = "members_extra";
    public static final String CHORES_EXTRA = "chores_extra";
    public static final String NAME_EXTRA = "name_extra";
    private FragmentPagerAdapter adapterViewPager;
    @BindView(R.id.vpPager)
    ViewPager vpPager;
    @BindView(R.id.groupName)
    TextView groupName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        groupName.setText(getIntent().getStringExtra(NAME_EXTRA));
        adapterViewPager = new ProfileAdapter(getSupportFragmentManager(), getIntent().getStringArrayExtra(MEMBERS_EXTRA), getIntent().getStringArrayExtra(CHORES_EXTRA));
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(0);
    }

    public static class ProfileAdapter extends FragmentPagerAdapter {

        private String[] users;
        private String[] chores;

        ProfileAdapter(FragmentManager fm, String[] users, String[] chores) {
            super(fm);
            this.users = users;
            this.chores = chores;
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= users.length) {
                return ProfileFragment.newInstance("Unassigned", chores);
            }
            return ProfileFragment.newInstance(users[position], new String[]{"A", "B", "C", "D", "E"});
        }

        @Override
        public int getCount() {
            return users.length + 1;
        }
    }
}
