package de.in.uulm.map.tinder.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import de.in.uulm.map.tinder.R;


/**
 * Created by alexanderrasputin on 23.05.17.
 */

public class FilterFragment extends Fragment implements
        FilterContract.View {

    private FilterContract.Presenter mPresenter;

    public SeekBar mSeekBar;

    public EditText mCategory;

    public TextView mSeekBarText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_settings, container,
                false);

        Toolbar settingsToolbar = (Toolbar) view.findViewById
                (R.id.settings_acitivty_toolbar);

        mCategory = (EditText) view.findViewById(R.id.settings_category);
        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.onCategoryClicked();
            }
        });

        mSeekBarText = (TextView) view.findViewById(R.id.radius_seek_bar_text);

        mSeekBar = (SeekBar) view.findViewById(R.id.radius_seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mSeekBarText.setText(""+getSeekBarProgress()+" km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mPresenter.onSeekBarChangeFinished(getSeekBarProgress());
            }
        });

        activity.setSupportActionBar(settingsToolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mPresenter.start();

        return view;
    }

    public void setCategory(String category) {

        mCategory.setText(category);
    }

    public void setSeekBarProgress(int progress) {

        mSeekBar.setProgress(progress - 1);
    }

    public int getSeekBarProgress() {

        return mSeekBar.getProgress() + 1;
    }

    @Override
    public void setPresenter(FilterContract.Presenter presenter) {

        mPresenter = presenter;
    }

    public void setCategoryEnabled(boolean enabled) {

        mCategory.setEnabled(enabled);
    }
}
