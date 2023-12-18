package com.github.gather.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse {

    private Long locationId;
    private String name;

    public LocationResponse(Long locationId, String name) {
        this.locationId = locationId;
        this.name = name;
    }
}


