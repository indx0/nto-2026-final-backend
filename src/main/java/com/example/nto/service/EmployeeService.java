package com.example.nto.service;

import com.example.nto.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto getByUsername(String username);
    void resetAttempts(String username);
}
