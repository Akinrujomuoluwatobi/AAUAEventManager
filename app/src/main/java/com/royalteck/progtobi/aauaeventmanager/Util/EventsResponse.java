package com.royalteck.progtobi.aauaeventmanager.Util;

import com.google.gson.annotations.SerializedName;
import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;

import java.util.ArrayList;
import java.util.List;


public class EventsResponse {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incomplete;

    @SerializedName("items")
    public List<EventModel> events = new ArrayList<>();

}
