package com.example.nto.service;

import com.example.nto.dto.BookingCreateMeetDto;
import com.example.nto.dto.BookingDto;
import com.example.nto.dto.PlaceDto;

import java.time.LocalDate;
import java.util.Map;

public interface PlaceService {
    PlaceDto getPlaceInfo(String username);
    Map<LocalDate, Boolean> getFreeDates(String username);
    BookingDto create(String username, BookingCreateMeetDto bookingCreateMeetDto);
    void freePlace(String username, BookingCreateMeetDto bookingCreateMeetDto);
}
