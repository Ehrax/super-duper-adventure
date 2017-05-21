package de.in.uulm.map.tinder.login;

/**
 * Created by maxka on 20.05.2017.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;

    public LoginPresenter(LoginContract.View view){
        mView = view;
    }
    @Override
    public void start() {

    }
}
