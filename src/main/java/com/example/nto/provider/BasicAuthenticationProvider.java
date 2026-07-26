package com.example.nto.provider;

import com.example.nto.entity.Employee;
import com.example.nto.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasicAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Value("${auth.wrong-logins-until-block}")
    private int wrongLoginsUntilBlock;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final UserDetails emp = userDetailsService.loadUserByUsername(name);

        Employee employeeFull = employeeRepository.getByUsername(name);

        if(employeeFull.getFailedLogins() >= wrongLoginsUntilBlock) {
            throw new LockedException("User account is locked. Contact an administrator");
        }

        if(emp.getUsername().equals(name) && !passwordEncoder.matches(password, emp.getPassword())) {
            employeeFull.setFailedLogins(employeeFull.getFailedLogins() + 1);
            employeeRepository.save(employeeFull);
            throw new BadCredentialsException("Wrong password. " + String.valueOf(wrongLoginsUntilBlock - employeeFull.getFailedLogins()) + " attempts left");
        }



        return new UsernamePasswordAuthenticationToken(emp, password, emp.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
