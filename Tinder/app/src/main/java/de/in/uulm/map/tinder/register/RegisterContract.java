package de.in.uulm.map.tinder.register;

import android.content.Intent;

import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by maxka on 05.06.2017.
 */

public interface RegisterContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{
        void startLoginActivity();
        void signUp(String email,String username, String password,String
                confirmPassword);
    }

    interface Backend{
        void startActivity(Intent intent);
    }

}
