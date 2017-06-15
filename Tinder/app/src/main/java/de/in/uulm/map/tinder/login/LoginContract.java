package de.in.uulm.map.tinder.login;

import android.content.Intent;

import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by maxka on 20.05.2017.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void signIn(String password,String username);

        void startRegisterActivity();
    }

    interface Backend{
        void startActivity(Intent intent);
    }
}
