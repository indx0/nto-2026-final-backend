package com.example.nto.util;

import com.example.nto.dto.EmployeeDto;
import com.example.nto.dto.PlaceDto;
import com.example.nto.entity.Authority;
import com.example.nto.entity.Booking;
import com.example.nto.entity.Employee;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@UtilityClass
public class EmployeeMapper {
    public EmployeeDto convertToDto(Employee employee) {
        Map<LocalDate, PlaceDto> dtoTreeMap = new TreeMap<>();
        for (Booking booking : employee.getBookingList()) {
            dtoTreeMap.put(booking.getDate(), PlaceMapper.convertToDto(booking.getPlace()));
        }
        List<String> authorities = new ArrayList<>();
        for(Authority a : employee.getAuthorities()) {
            authorities.add(a.getAuthority());
        }

        return new EmployeeDto(employee.getName(), employee.getPhotoUrl(), dtoTreeMap, authorities);
    }
}
