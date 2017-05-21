package de.in.uulm.map.tinder.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.util.ActivityUtils;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame_layout);

        LoginFragment fragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if(fragment == null) {
            fragment = new LoginFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.content_frame);
        }

        LoginPresenter presenter = new LoginPresenter(fragment);
        fragment.setPresenter(presenter);

    }


}
