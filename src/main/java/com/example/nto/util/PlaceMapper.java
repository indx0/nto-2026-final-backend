package com.example.nto.util;

import com.example.nto.dto.PlaceDto;
import com.example.nto.entity.Place;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlaceMapper {
    public PlaceDto convertToDto(Place place) {
        return new PlaceDto(place.getId(), place.getPlace(), place.getDescription());
    }
}
