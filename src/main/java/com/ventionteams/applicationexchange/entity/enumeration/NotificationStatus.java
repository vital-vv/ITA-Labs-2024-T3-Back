package com.ventionteams.applicationexchange.entity.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NotificationStatus {

    @JsonProperty("new")
    NEW,
    @JsonProperty("seen")
    SEEN
}
