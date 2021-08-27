package com.sanket.doodle.streams;

import com.sanket.doodle.domain.Employee;
import com.sanket.doodle.domain.Programmer;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class CustomSerializer implements Serializer<Employee> {
    JsonSerializer<Employee> employeeJsonSerializer;
    JsonSerializer<Programmer> programmerJsonSerializer;

    public CustomSerializer() {
        super();
        employeeJsonSerializer = new JsonSerializer<>();
        programmerJsonSerializer = new JsonSerializer<>();
        employeeJsonSerializer.setAddTypeInfo(true);
        programmerJsonSerializer.setAddTypeInfo(true);
    }

    @Override
    public byte[] serialize(String topic, Employee employee) {
        if(employee instanceof Programmer){
            return programmerJsonSerializer.serialize(topic, (Programmer) employee);
        }
        return employeeJsonSerializer.serialize(topic, employee);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Employee employee) {
        if(employee instanceof Programmer){
            return programmerJsonSerializer.serialize(topic, headers, (Programmer) employee);
        }
        return employeeJsonSerializer.serialize(topic, headers, employee);
    }
}
