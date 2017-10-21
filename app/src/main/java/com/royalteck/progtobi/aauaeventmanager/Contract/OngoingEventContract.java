package com.royalteck.progtobi.aauaeventmanager.Contract;

import com.royalteck.progtobi.aauaeventmanager.Adapters.UpEventAdapter;
import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;
import com.royalteck.progtobi.aauaeventmanager.Presenter.OngoingEventPresenter;

import java.util.List;

/**
 * Created by PROG. TOBI on 14-Aug-17.
 */

public class OngoingEventContract {

    public interface View {

        void showmessage(String s);

        void setDevelopersAdapter(List<EventModel> mDevelopersAdapter);

        void setPresenter(OngoingEventPresenter ongoingEventPresenter);

        void checkInternet();

        void showLoading(boolean b);

        void showDeveloperDetails(EventModel eventModel);
    }
}
