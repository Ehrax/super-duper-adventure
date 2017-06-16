package de.in.uulm.map.tinder.main.groupchat;

/**
 * Created by alexanderrasputin on 11.05.17.
 */

public class GroupChatPresenter implements GroupChatContract.Presenter {

    private final GroupChatContract.View mView;

    public GroupChatPresenter(GroupChatContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
