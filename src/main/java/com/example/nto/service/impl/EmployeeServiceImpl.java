package com.example.nto.service.impl;

import com.example.nto.dto.EmployeeDto;
import com.example.nto.entity.Employee;
import com.example.nto.exception.EmployeeNotFoundException;
import com.example.nto.repository.EmployeeRepository;
import com.example.nto.service.EmployeeService;
import com.example.nto.util.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getByUsername(String username) {
        return EmployeeMapper.convertToDto(employeeRepository.findByUsername(username));
    }

    @Override
    @Transactional
    public void resetAttempts(String username) {
        Employee emp = employeeRepository.getByUsername(username);
        if(emp == null) {
            throw new EmployeeNotFoundException("Employee with " + username + " name not found!");
        }
        emp.setFailedLogins(0);
        employeeRepository.save(emp);
    }
}
