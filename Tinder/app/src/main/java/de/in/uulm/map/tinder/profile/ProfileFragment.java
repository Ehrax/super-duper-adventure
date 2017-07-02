package de.in.uulm.map.tinder.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.in.uulm.map.tinder.R;

/**
 * Created by Jona on 02.07.17.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private ProfileContract.Presenter mPresenter;

    public ProfileFragment() {

    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mPresenter.start();

        return view;
    }
}
