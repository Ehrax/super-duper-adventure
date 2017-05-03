package de.in.uulm.map.tinder.events.joined;

import de.in.uulm.map.tinder.events.EventsContract;
import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public interface JoinedContract {

    interface JoinedView extends EventsContract.TabView<JoinedPresenter> {};

    interface JoinedPresenter extends BasePresenter{};
}
