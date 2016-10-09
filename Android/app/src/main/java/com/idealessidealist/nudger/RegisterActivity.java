package com.idealessidealist.nudger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.idealessidealist.nudger.connector.GoogleConnector;
import com.idealessidealist.nudger.model.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.name)
    TextInputEditText name;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.unfocus)
    FrameLayout unfocus;

    private View.OnFocusChangeListener focusRemove = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            unfocus.requestFocus();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ButterKnife.findById(this, R.id.unfocus).requestFocus();
    }

    @BindView(R.id.completeRegister)
    Button register;

    @OnClick(R.id.completeRegister)
    public void register() {
        register.setEnabled(false);
        register.setSelected(true);
        email.clearFocus();
        name.clearFocus();
        password.clearFocus();
        GoogleConnector.register(email.getText().toString(), name.getText().toString(), password.getText().toString())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(user -> {
            if (user.code() == 200) {
                UserService.getInstance().setUser(user.body());
                startActivity(new Intent(getApplicationContext(), ListActivity.class));
                return;
            }
            register.setEnabled(true);
            register.setSelected(false);
            displayConnectFailedToast();
        });
    }

    private Toast connectionFailed;

    private void displayConnectFailedToast() {
        if (connectionFailed != null) {
            connectionFailed.cancel();
        }
        connectionFailed = Toast.makeText(this, "Failed to Connect", Toast.LENGTH_SHORT);
        connectionFailed.show();
    }

}

