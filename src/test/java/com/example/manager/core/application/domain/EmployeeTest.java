package com.example.manager.core.application.domain;

import com.example.manager.core.domain.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class EmployeeTest {
    @Test
    public void test() {
        Employee employee = new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username",
                "password", "securityAccess");
        assertThat(employee.getTeam()).isEqualTo("team");
        assertThat(employee.getAvailability()).isEqualTo("availability");
        assertThat(employee.getName()).isEqualTo("name");
        assertThat(employee.getSurname()).isEqualTo("surname");
        assertThat(employee.getUsername()).isEqualTo("username");
        assertThat(employee.getPassword()).isEqualTo("password");
        assertThat(employee.toString()).isEqualTo("Employee(uid=1, team=team, tasks=tasks, projects=projects, availability=availability, name=name," +
                " surname=surname, username=username, password=password, securityAccess=securityAccess)");
        assertThat(employee.getSecurityAccess()).isEqualTo("securityAccess");
        assertDoesNotThrow(() -> employee.setTeam("team"));
        assertDoesNotThrow(() -> employee.setAvailability("availability"));
        assertDoesNotThrow(() -> employee.setName("name"));
        assertDoesNotThrow(() -> employee.setSurname("surname"));
        assertDoesNotThrow(() -> employee.setUsername("username"));
        assertDoesNotThrow(() -> employee.setPassword("password"));
        assertDoesNotThrow(() -> employee.setSecurityAccess("securityAccess"));
        assertThat(employee.equals(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username",
                "password", "securityAccess"))).isTrue();

        assertThat(employee.equals(new Employee(2L,"team","tasks","projects",
                "availability","name","surname","username",
                "password","securityAccess"))).isFalse();

        assertThat(employee.equals(new Employee(1L,"team1","tasks","projects",
                "availability","name","surname","username",
                "password","securityAccess"))).isFalse();

        assertThat(employee.equals(new Employee(1L,"team","tasks1","projects",
                "availability","name","surname","username",
                "password","securityAccess"))).isFalse();

        assertThat(employee.equals(new Employee(1L,"team","tasks","projects1",
                "availability","name","surname","username",
                "password", "securityAccess"))).isFalse();

        assertThat(employee.equals(new Employee(1L,"team","tasks","projects",
                "availability1","name","surname","username",
                "password", "securityAccess"))).isFalse();

        assertThat(employee.equals(new Employee(1L,"team","tasks","projects",
                "availability","name1","surname","username",
                "password", "securityAccess"))).isFalse();

        assertThat(employee.equals(new Employee(1L,"team","tasks","projects",
                "availability","name","surname1","username",
                "password", "securityAccess"))).isFalse();

        assertThat(employee.equals(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username1",
                "password", "securityAccess"))).isFalse();

        assertThat(employee.equals(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username",
                "password1", "securityAccess"))).isFalse();

        assertThat(employee.equals(new Employee(1L,"team","tasks","projects",
                "availability","name","surname","username",
                "password", "securityA4ccess"))).isFalse();

        assertThat(employee).isEqualTo(employee);
    }
}
