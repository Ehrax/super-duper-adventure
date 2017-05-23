package de.in.uulm.map.tinder.settings;

/**
 * Created by alexanderrasputin on 23.05.17.
 */

public class SettingsPresenter implements SettingsContract.Presenter {

    public SettingsPresenter(SettingsContract.View mView) {

        this.mView = mView;
    }

    private final SettingsContract.View mView;

    @Override
    public void start() {

    }
}
