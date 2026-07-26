package com.example.nto.controller;


import com.example.nto.dto.EmployeeDto;
import com.example.nto.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @PostMapping("/login")
    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Something went wrong"),
            @ApiResponse(responseCode = "401", description = "Unauthorized") })
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<EmployeeDto> login(Authentication authentication) {
        return new ResponseEntity<>(employeeService.getByUsername(authentication.getName()), HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "Get profile info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Something went wrong"),
            @ApiResponse(responseCode = "401", description = "Unauthorized") })
    @ResponseStatus(code = HttpStatus.OK)
    public EmployeeDto getByCode(Authentication authentication) {
        return employeeService.getByUsername(authentication.getName());
    }

    @PatchMapping("/reset_attempts/{username}")
    @Operation(summary = "Reset failed login attempts (ADMIN)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Something went wrong"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Not Admin")
    })
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Void> resetAttempts(@PathVariable String username) {
        employeeService.resetAttempts(username);
        return ResponseEntity.ok().build();
    }
}
