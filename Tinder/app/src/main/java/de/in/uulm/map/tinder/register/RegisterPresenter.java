package de.in.uulm.map.tinder.register;

import android.content.Context;
import android.content.Intent;

import de.in.uulm.map.tinder.login.LoginActivity;
import de.in.uulm.map.tinder.login.LoginPresenter;

/**
 * Created by maxka on 05.06.2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View mView;
    private final RegisterContract.Backend mBackend;

    private Context mApplicationContext;
    private Context mContext;

    public RegisterPresenter(RegisterContract.View view,RegisterContract
            .Backend backend, Context applicationContext,Context context){
        mView = view;
        mBackend = backend;
        mApplicationContext = applicationContext;
        mContext = context;

    }

    @Override
    public void start() {

    }

    @Override
    public void signUp(String email,String username,String password, String
            confirmPassword){

    }

    @Override
    public void startLoginActivity(){
        mBackend.startActivity(new Intent(mContext, LoginActivity.class));
    }

}
