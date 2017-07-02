package de.in.uulm.map.tinder.profile;

/**
 * Created by Jona on 02.07.17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View mView;

    public ProfilePresenter(ProfileContract.View view) {

        mView = view;
    }

    @Override
    public void start() {

    }
}
