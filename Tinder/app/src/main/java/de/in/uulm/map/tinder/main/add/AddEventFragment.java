package de.in.uulm.map.tinder.main.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.R;

/**
 * Created by Jona on 21.05.2017.
 */

public class AddEventFragment extends Fragment implements AddEventContract.View {

    public AddEventContract.Presenter mPresenter;

    public static AddEventFragment newInstance() {

        return new AddEventFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_add_event, container, false);
        return view;
    }

    @Override
    public void setPresenter(AddEventContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void onFragmentBecomesVisible() {

    }
}
