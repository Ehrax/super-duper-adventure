package de.in.uulm.map.tinder.main.groupchat;

import de.in.uulm.map.tinder.main.MainContract;
import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatContract {

    interface  Presenter extends BasePresenter{}

    interface View extends MainContract.MainView<Presenter> { }

}
