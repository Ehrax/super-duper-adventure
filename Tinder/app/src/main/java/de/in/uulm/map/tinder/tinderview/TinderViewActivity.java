package de.in.uulm.map.tinder.tinderview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.in.uulm.map.tinder.R;
import de.in.uulm.map.tinder.util.ActivityUtils;

/**
 * Created by maxka on 26.06.2017.
 */

public class TinderViewActivity extends AppCompatActivity implements
        TinderViewContract.Backend{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame_layout);

        TinderViewFragment fragment = (TinderViewFragment)
                getSupportFragmentManager().findFragmentById(R.id
                        .content_frame);

        if(fragment == null){
            fragment = TinderViewFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment,R.id.content_frame);
        }

        TinderViewPresenter presenter = new TinderViewPresenter(fragment,
                getApplicationContext(),this,this);
        fragment.setPresenter(presenter);
    }

    @Override
    public void startActivity(Intent intent) {

        super.startActivity(intent);
    }
}
