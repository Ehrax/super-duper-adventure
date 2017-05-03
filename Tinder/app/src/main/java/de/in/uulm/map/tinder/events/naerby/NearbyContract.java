package de.in.uulm.map.tinder.events.naerby;

import de.in.uulm.map.tinder.events.EventsContract;
import de.in.uulm.map.tinder.util.BasePresenter;
import de.in.uulm.map.tinder.util.BaseView;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public interface NearbyContract {
    interface NearbyView extends EventsContract.TabView<NearbyPresenter> {};

    interface NearbyPresenter extends BasePresenter{};
}
