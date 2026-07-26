package com.example.nto.controller;

import com.example.nto.dto.BookingCreateDto;
import com.example.nto.dto.PlaceDto;
import com.example.nto.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/booking/")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/booking")
    @Operation(summary = "Get bookings (Today + 3 days)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Something went wrong"),
            @ApiResponse(responseCode = "401", description = "Code does not exist"),
    })
    @ResponseStatus(code = HttpStatus.OK)
    public Map<LocalDate, List<PlaceDto>> getByDate() {
        return bookingService.getFreePlace();
    }

    @PostMapping("/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Something went wrong"),
            @ApiResponse(responseCode = "401", description = "Code does not exist"),
            @ApiResponse(responseCode = "409", description = "Booking on this day already exists or employee has another booking on this days"),
    })
    public void create(Authentication authentication, @RequestBody BookingCreateDto bookingCreateDto) {
        bookingService.create(authentication.getName(), bookingCreateDto);
    }


}
