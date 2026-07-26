package com.example.nto.dto;

import com.example.nto.entity.Booking;
import com.example.nto.entity.Employee;
import com.example.nto.util.PlaceMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private String name;
    private String photoUrl;
    private Map<LocalDate, PlaceDto> booking;
    private List<String> roles;
}
