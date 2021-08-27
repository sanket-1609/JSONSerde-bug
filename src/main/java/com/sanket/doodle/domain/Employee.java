package com.sanket.doodle.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Employee {
    String name;
    Long salary;

    public Employee(String name, Long salary) {
        this.name = name;
        this.salary = salary;
    }
}
