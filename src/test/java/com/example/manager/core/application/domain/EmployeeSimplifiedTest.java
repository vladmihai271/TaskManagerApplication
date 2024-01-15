package com.example.manager.core.application.domain;

import com.example.manager.core.domain.EmployeeSimplified;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class EmployeeSimplifiedTest {
    @Test
    public void test() {
        EmployeeSimplified employeeSimplified = new EmployeeSimplified("team",
                "availability", "name", "surname", "username", "password",
                "securityAccess");
        assertThat(employeeSimplified.getTeam().equals("team"));
        assertThat(employeeSimplified.getAvailability().equals("availability"));
        assertThat(employeeSimplified.getName().equals("name"));
        assertThat(employeeSimplified.getSurname().equals("surname"));
        assertThat(employeeSimplified.getUsername().equals("username"));
        assertThat(employeeSimplified.getPassword().equals("password"));
        assertThat(employeeSimplified.getSecurityAccess().equals("securityAccess"));
        assertThat(employeeSimplified.hashCode()).isNotEqualTo(new EmployeeSimplified("teamw",
                "availability", "name", "surname", "username", "password",
                "securityAccess").hashCode());
        assertDoesNotThrow(() -> employeeSimplified.setTeam("team"));
        assertDoesNotThrow(() -> employeeSimplified.setAvailability("availability"));
        assertDoesNotThrow(() -> employeeSimplified.setName("name"));
        assertDoesNotThrow(() -> employeeSimplified.setSurname("surname"));
        assertDoesNotThrow(() -> employeeSimplified.setUsername("username"));
        assertDoesNotThrow(() -> employeeSimplified.setPassword("password"));
        assertDoesNotThrow(() -> employeeSimplified.setSecurityAccess("securityAccess"));
        assertThat(employeeSimplified.toString()).
                isEqualTo("EmployeeSimplified(team=team, availability=availability, name=name, surname=surname, username=username, password=password, securityAccess=securityAccess)");
        assertThat(employeeSimplified.equals(new EmployeeSimplified("team",
                "availability", "name", "surname",
                "username", "password","securityAccess"))).isTrue();
        assertThat(employeeSimplified.equals(new EmployeeSimplified("team",
                "availability", "name", "surname",
                "username", "password2", "securityAccess"))).isFalse();
        assertThat(employeeSimplified.equals(new EmployeeSimplified("2team",
                "availability", "name", "surname",
                "username", "password","securityAccess"))).isFalse();
        assertThat(employeeSimplified.equals(new EmployeeSimplified("team",
                "avai2lability", "name", "surname",
                "username", "password","securityAccess"))).isFalse();
        assertThat(employeeSimplified.equals(new EmployeeSimplified("team",
                "availability", "nam2e", "surname",
                "username", "password","securityAccess"))).isFalse();
        assertThat(employeeSimplified.equals(new EmployeeSimplified("team",
                "availability", "name", "sur2name",
                "username", "password","securityAccess"))).isFalse();
        assertThat(employeeSimplified.equals(new EmployeeSimplified("team",
                "availability", "name", "surname",
                "usern2ame", "password","securityAccess"))).isFalse();
        assertThat(employeeSimplified.equals(new EmployeeSimplified("team",
                "availability", "name", "surname",
                "username", "password","security2Access"))).isFalse();
    }
}
