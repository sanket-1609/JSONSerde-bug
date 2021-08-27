package com.sanket.doodle.streams;

import com.sanket.doodle.domain.Employee;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CustomSerde implements Serde<Employee> {
    JsonDeserializer<Employee> employeeJsonDeserializer;
    CustomSerializer customSerializer;

    public CustomSerde() {
        employeeJsonDeserializer = new JsonDeserializer<>();
        employeeJsonDeserializer.trustedPackages("*");
        customSerializer = new CustomSerializer();
    }

    @Override
    public Serializer<Employee> serializer() {
        return customSerializer;
    }

    @Override
    public Deserializer<Employee> deserializer() {
        return employeeJsonDeserializer;
    }
}
