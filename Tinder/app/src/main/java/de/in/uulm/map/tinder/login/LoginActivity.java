package de.in.uulm.map.tinder.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.main.MainActivity;
import de.in.uulm.map.tinder.util.ActivityUtils;
import de.in.uulm.map.tinder.util.DateHelper;

import java.util.Date;

public class LoginActivity extends AppCompatActivity implements LoginContract.Backend {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame_layout);

        SharedPreferences sharedPref = getSharedPreferences(getString(R
                .string.store_account),MODE_PRIVATE);

        String expireDateString = sharedPref.getString(getString(R.string
                .store_token_expire),null);
        if(expireDateString != null){
            Date expireDate = DateHelper.convertExpireDateStringToDate
                    (expireDateString);
            if(expireDate.compareTo(new Date())>0){
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }

        LoginFragment fragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if(fragment == null) {
            fragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.content_frame);
        }

        LoginPresenter presenter = new LoginPresenter(fragment,
                getApplicationContext(),this,this);
        fragment.setPresenter(presenter);

    }

    @Override
    public void startActivity(Intent intent){

        super.startActivity(intent);
    }


}
