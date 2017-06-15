package de.in.uulm.map.tinder.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.R;


/**
 * Created by alexanderrasputin on 23.05.17.
 */

public class SettingsFragment extends Fragment implements
        SettingsContract.View {

    private SettingsContract.Presenter mPresenter;

    public static final  String EVENT_RADIUS = "event_radius";
    public static final String EVENT_CATEGORY = "event_category";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_settings, container,
                false);

        Toolbar settingsToolbar = (Toolbar) view.findViewById
                (R.id.settings_acitivty_toolbar);

        activity.setSupportActionBar(settingsToolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        return view;
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {

        mPresenter = presenter;
    }
}
