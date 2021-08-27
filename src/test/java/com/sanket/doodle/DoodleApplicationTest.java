package com.sanket.doodle;

import com.sanket.doodle.streams.StreamsTopology;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Objects;

@SpringBootTest
@EmbeddedKafka(partitions = 1, ports = 9092, topics = {"INPUT_1", "INPUT_2", "INPUT_3", "INPUT_4"})
class DoodleApplicationTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void main() {
        Awaitility
                .waitAtMost(Duration.ofSeconds(20))
                .until(StreamsTopology::allPopulated);
        Assert.isTrue(Objects.equals(StreamsTopology.p11.getPreferredCodingLanguage(), "Java"), "CustomSerde failing to deser properly");
        Assert.isTrue(Objects.equals(StreamsTopology.p22.getPreferredCodingLanguage(), "Java"), "CustomSerde failing to deser properly");

        Assert.isTrue(Objects.equals(StreamsTopology.p1.getPreferredCodingLanguage(), "Java"), "JsonSerde failing to deser properly");
        Assert.isTrue(Objects.equals(StreamsTopology.p2.getPreferredCodingLanguage(), "Java"), "JsonSerde failing to deser properly");

    }
}