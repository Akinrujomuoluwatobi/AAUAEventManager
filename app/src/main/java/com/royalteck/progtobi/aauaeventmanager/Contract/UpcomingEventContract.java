package com.royalteck.progtobi.aauaeventmanager.Contract;

import com.royalteck.progtobi.aauaeventmanager.Adapters.UpEventAdapter;
import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;
import com.royalteck.progtobi.aauaeventmanager.Presenter.UpcomingEventPresenter;
import com.royalteck.progtobi.aauaeventmanager.Util.EventsResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PROG. TOBI on 14-Aug-17.
 */

public class UpcomingEventContract {

    public interface View {

        void showmessage(String s);

        void setDevelopersAdapter(List<EventModel> eventlist);

        void showLoading(boolean b);

        void checkInternet();

        void saveEventsDetails(EventsResponse response);

        void setPresenter(UpcomingEventPresenter upcomingEventPresenter);

        void showDeveloperDetails(EventModel eventModel);
    }
}
