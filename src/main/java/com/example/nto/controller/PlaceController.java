package com.example.nto.controller;

import com.example.nto.dto.BookingCreateMeetDto;
import com.example.nto.dto.BookingDto;
import com.example.nto.dto.PlaceDto;
import com.example.nto.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;
    @GetMapping()
    @Operation(summary = "Get meeting room info on current account (peregovor1, peregovor2, peregovor3)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Something went wrong"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<PlaceDto> getPlace(Authentication authentication) {
        return new ResponseEntity<>(placeService.getPlaceInfo(authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("/schedule")
    @Operation(summary = "Get meeting room schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Something went wrong"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<Map<LocalDate, Boolean>> getShedule(Authentication authentication) {
        return new ResponseEntity<>(placeService.getFreeDates(authentication.getName()), HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Free a room on a meeting room tablet screen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Something went wrong"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
    })
    public ResponseEntity<Void> freeRoom(@RequestBody BookingCreateMeetDto dto, Authentication authentication) {
        placeService.freePlace(authentication.getName(), dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/book")
    @Operation(summary = "Create a booking on a meeting room tablet screen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Something went wrong"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingCreateMeetDto dto, Authentication authentication) {
        return new ResponseEntity<>(placeService.create(authentication.getName(), dto), HttpStatus.CREATED);
    }
}
