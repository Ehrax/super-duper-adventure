package de.in.uulm.map.tinder.groupchat;

import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatContract {

    interface  Presenter extends BasePresenter{}

    interface View extends BaseView<Presenter> { }

}
