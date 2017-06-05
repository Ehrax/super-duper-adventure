package de.in.uulm.map.tinder.login;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.in.uulm.map.tinder.R;


/**
 * Created by maxka on 20.05.2017.
 */

public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        TextView txtViewLogo = (TextView)view.findViewById(R.id.txt_view_logo);
        txtViewLogo.setTypeface(Typeface.createFromAsset(getContext()
                .getAssets(),"Youth_and_Beauty.ttf"));

        Button btnSignIn = (Button) view.findViewById(R.id.btn_signin);
        final EditText editTextPassword = (EditText) view.findViewById(R.id
                .edit_txt_password_login);
        final EditText editTextEmail = (EditText) view.findViewById(R.id
                .edit_txt_email_login);
        TextView txtViewRegister = (TextView)view.findViewById(R.id
                .txt_view_register_link);

        btnSignIn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mPresenter.signIn(editTextPassword.getText().toString(),editTextEmail
                        .getText().toString());
            }
        });

        txtViewRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPresenter.startRegisterActivity();
            }
        });

        return view;

    }


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

        mPresenter = presenter;
    }

}
