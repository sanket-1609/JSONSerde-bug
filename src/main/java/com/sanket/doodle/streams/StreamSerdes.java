package com.sanket.doodle.streams;

import com.sanket.doodle.domain.Employee;
import org.springframework.kafka.support.serializer.JsonSerde;

public class StreamSerdes {
    private static final JsonSerde<Employee> employeeJsonSerde;
    private static final CustomSerde customSerde;

    static {
        employeeJsonSerde = new JsonSerde<>(Employee.class);
        customSerde = new CustomSerde();
    }

    public static JsonSerde<Employee> employeeJsonSerde(){
        return employeeJsonSerde;
    }

    public static CustomSerde customSerde(){
        return customSerde;
    }

}
