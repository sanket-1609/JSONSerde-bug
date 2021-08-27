package com.sanket.doodle.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Programmer extends Employee {
    Long bonus;
    String preferredCodingLanguage;
    public Programmer(String name, Long salary, Long bonus, String preferredCodingLanguage) {
        super(name, salary);
        this.bonus = bonus;
        this.preferredCodingLanguage = preferredCodingLanguage;
    }


}
