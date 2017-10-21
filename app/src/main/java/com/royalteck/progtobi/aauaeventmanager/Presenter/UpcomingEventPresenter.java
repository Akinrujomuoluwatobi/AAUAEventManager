package com.royalteck.progtobi.aauaeventmanager.Presenter;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.royalteck.progtobi.aauaeventmanager.APIService;
import com.royalteck.progtobi.aauaeventmanager.Adapters.UpEventAdapter;
import com.royalteck.progtobi.aauaeventmanager.ApiClient;
import com.royalteck.progtobi.aauaeventmanager.Contract.UpcomingEventContract;
import com.royalteck.progtobi.aauaeventmanager.EventManagerApp;
import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;
import com.royalteck.progtobi.aauaeventmanager.Util.EventsResponse;
import com.royalteck.progtobi.aauaeventmanager.Util.SharedPreferenceHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PROG. TOBI on 14-Aug-17.
 */

public class UpcomingEventPresenter {
    private final APIService eventManApi;
    UpcomingEventContract.View mview;
    private UpEventAdapter meventAdapter;
    List<EventModel> mEventList;

    public UpcomingEventPresenter(UpcomingEventContract.View view) {
        mview = view;
        view.setPresenter(this);
        eventManApi = ApiClient.getClient().create(APIService.class);
        mEventList = new ArrayList<>();
        //setEventsAdapter();
        retieveAllEvent();
    }

    public void retieveAllEvent() {
        mview.checkInternet();
    }

    public void fetchEventOnline() {
        mview.showLoading(true);
        eventManApi.getData().enqueue(new Callback<ArrayList<EventModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EventModel>> call, Response<ArrayList<EventModel>> response) {
                mEventList = new ArrayList<>();
                mEventList = response.body();
                if (!mEventList.isEmpty()) {
                    //mview.saveEventsDetails((EventsResponse) mEventList);
                    populateEventsList();
                }
                mview.setDevelopersAdapter(mEventList);
                mview.showLoading(false);
            }

            @Override
            public void onFailure(Call<ArrayList<EventModel>> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                mview.showmessage(t.getMessage());
                mview.showLoading(false);
            }

        });

    }

    public void fetchEventLocally() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mEventList = SharedPreferenceHandler.
                        fetchDeveloperRecords((Activity) getView()).events;
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                populateEventsList();
            }
        }.execute();
    }

    private UpcomingEventContract.View getView() {
        return mview;
    }

    public void populateEventsList() {
        if (mEventList.size() < 1) {
            mview.showmessage("Events list is empty");
        } else {
            mview.setDevelopersAdapter(mEventList);
            //meventAdapter.notifyDataSetChanged();
        }
    }

    public void OnShowDeveloperDetails(int position) {
        mview.showDeveloperDetails(mEventList.get(position));
    }

    /*void saveEventsDetails(final EventsResponse developersResponse) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferenceHandler.saveDeveloperRecords((Activity) getView(), developersResponse);
            }
        };
        AsyncTask.execute(runnable);

    }*/

    /*public void showMessage() {
        mview.showmessage("Welcome");
    }*/
}
