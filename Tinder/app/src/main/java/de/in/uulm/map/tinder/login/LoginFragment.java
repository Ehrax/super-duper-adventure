package de.in.uulm.map.tinder.login;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.in.uulm.map.tinder.R;


/**
 * Created by maxka on 20.05.2017.
 */

public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        TextView txtViewLogo = (TextView)view.findViewById(R.id.txt_view_logo);
        txtViewLogo.setTypeface(Typeface.createFromAsset(getContext()
                .getAssets(),"Youth_and_Beauty.ttf"));
        return view;
    }


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

        mPresenter = presenter;
    }

}
