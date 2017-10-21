package com.royalteck.progtobi.aauaeventmanager.Contract;

import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;
import com.royalteck.progtobi.aauaeventmanager.Presenter.EventViewPresenter;

/**
 * Created by PROG. TOBI on 22-Aug-17.
 */

public class EventViewContract {
    public interface View{

        void setPresenter(EventViewPresenter eventViewPresenter);

        void showDevDetails(EventModel event);

        void shareDevDetails(EventModel event);
    }
}
