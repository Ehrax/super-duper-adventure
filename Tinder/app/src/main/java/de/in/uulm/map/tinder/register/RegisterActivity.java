package de.in.uulm.map.tinder.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.util.ActivityUtils;

/**
 * Created by maxka on 05.06.2017.
 */

public class RegisterActivity extends AppCompatActivity implements
        RegisterContract.Backend {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame_layout);

        RegisterFragment fragment = (RegisterFragment)
                getSupportFragmentManager().findFragmentById(R.id
                        .content_frame);
        if(fragment == null){
            fragment= RegisterFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment,R.id.content_frame);
        }

        RegisterPresenter presenter = new RegisterPresenter(fragment,this,
                getApplicationContext(),this);

        fragment.setPresenter(presenter);
    }

}
