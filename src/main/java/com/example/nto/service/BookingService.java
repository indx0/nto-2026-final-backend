package com.example.nto.service;

import com.example.nto.dto.BookingCreateDto;
import com.example.nto.dto.BookingDto;
import com.example.nto.dto.PlaceDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingService {
    Map<LocalDate, List<PlaceDto>> getFreePlace();

    BookingDto create(String username, BookingCreateDto bookingCreateDto);
}
