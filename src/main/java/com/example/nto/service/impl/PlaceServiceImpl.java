package com.example.nto.service.impl;

import com.example.nto.dto.BookingCreateMeetDto;
import com.example.nto.dto.BookingDto;
import com.example.nto.dto.PlaceDto;
import com.example.nto.entity.Booking;
import com.example.nto.entity.Employee;
import com.example.nto.entity.Place;
import com.example.nto.exception.BookingAlreadyExistsException;
import com.example.nto.exception.BookingNotFoundException;
import com.example.nto.repository.BookingRepository;

import com.example.nto.repository.PlaceRepository;
import com.example.nto.service.PlaceService;
import com.example.nto.util.BookingMapper;
import com.example.nto.util.PlaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final BookingRepository employeeRepository;
    private final BookingRepository bookingRepository;

    @Value("${booking.days-ahead}")
    private int daysAhead;

    @Override
    @Transactional(readOnly = true)
    public PlaceDto getPlaceInfo(String username) {
        Place place = placeRepository.findByAccount_Username(username);
        return PlaceMapper.convertToDto(place);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<LocalDate, Boolean> getFreeDates(String username) {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate end = today.plusDays(daysAhead);

        Place place = placeRepository.findByAccount_Username(username);
        //Employee placeAcc = place.getAccount();
        Map<LocalDate, Boolean> res = new LinkedHashMap<>();

        for (int i = 0; i <= daysAhead; i++) {
            LocalDate currentDate = today.plusDays(i);
            Optional<Booking> bookings = bookingRepository.findByDateAndPlace(currentDate, place);
            if(bookings.isEmpty()) {
                res.put(currentDate, true);
            }
            else {
                res.put(currentDate, false);
            }
        }

        return res;
    }

    @Override
    @Transactional
    public BookingDto create(String username, BookingCreateMeetDto bookingCreateMeetDto) {
        LocalDate date = bookingCreateMeetDto.getDate();
        LocalDate today = LocalDate.now(ZoneId.systemDefault());

        if (date.isBefore(today) || date.isAfter(today.plusDays(daysAhead))) {
            throw new IllegalArgumentException("Date is out of booking window");
        }

        Place place = placeRepository.findByAccount_Username(username);
        Employee placeAcc = place.getAccount();

        if (bookingRepository.findByDateAndPlace(date, place).isPresent()) {
            throw new BookingAlreadyExistsException("This place already has booking on " + date);
        }

        Booking bk = new Booking();
        bk.setPlace(place);
        bk.setDate(bookingCreateMeetDto.getDate());
        bk.setEmployee(placeAcc);
        return BookingMapper.convertToDto(bookingRepository.save(bk));
    }

    @Override
    @Transactional
    public void freePlace(String username, BookingCreateMeetDto bookingCreateMeetDto) {
        LocalDate date = bookingCreateMeetDto.getDate();
        LocalDate today = LocalDate.now(ZoneId.systemDefault());

        if (date.isBefore(today) || date.isAfter(today.plusDays(daysAhead))) {
            throw new IllegalArgumentException("Date is out of booking window");
        }

        Place place = placeRepository.findByAccount_Username(username);
        Employee placeAcc = place.getAccount();

        Optional<Booking> bk = bookingRepository.findByDateAndPlace(date, place);

        if(bk.isEmpty()) {
            throw new BookingNotFoundException("Booking not found on this date");
        }

        bookingRepository.deleteById(bk.get().getId());
    }
}
