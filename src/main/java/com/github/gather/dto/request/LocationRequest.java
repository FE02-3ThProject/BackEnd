package com.github.gather.dto.request;

public class LocationRequest {


    private String locationName;

    public LocationRequest() {
    }

    public LocationRequest(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

}
