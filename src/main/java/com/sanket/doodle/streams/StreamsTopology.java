

package com.sanket.doodle.streams;


import com.sanket.doodle.domain.Employee;
import com.sanket.doodle.domain.Programmer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StreamsTopology {
    private final StreamsBuilder streamsBuilder;
    private final KafkaTemplate <Long, Employee> employeeKafkaTemplate;
    public static Employee e1,e2,e11,e22;
    public static Programmer p1,p2,p11,p22;

    public StreamsTopology(StreamsBuilder streamsBuilder, KafkaTemplate<Long, Employee> employeeKafkaTemplate) {
        this.streamsBuilder = streamsBuilder;
        this.employeeKafkaTemplate = employeeKafkaTemplate;
        initTopology();
    }

    public static boolean allPopulated(){
        return e1 != null
                && e2 != null
                && e11 != null
                && e22 != null
                && p1 != null
                && p2 != null
                && p11 != null
                && p22 != null;
    }

    private void initTopology(){
        employeeKafkaTemplate
                .send("INPUT_1", 1L, new Employee("SANKET", 100L))
                .addCallback(employeeSendResult -> log.info("SENT SUCCESS EMPLOYEE TO 1"),
                        throwable -> log.error("Uh OH", throwable)
                );
        employeeKafkaTemplate
                .send("INPUT_1", 1L, new Programmer("ARPIT", 100L, 99L, "Java"))
                .addCallback(employeeSendResult -> log.info("SENT SUCCESS PROGRAMMER TO 1"),
                        throwable -> log.error("Uh OH", throwable)
                );

        employeeKafkaTemplate
                .send("INPUT_3", 1L, new Employee("SANKET1", 100L))
                .addCallback(employeeSendResult -> log.info("SENT SUCCESS EMPLOYEE TO 3"),
                        throwable -> log.error("Uh OH", throwable)
                );
        employeeKafkaTemplate
                .send("INPUT_3", 1L, new Programmer("ARPIT1", 100L, 99L, "Java"))
                .addCallback(employeeSendResult -> log.info("SENT SUCCESS PROGRAMMER TO 3"),
                        throwable -> log.error("Uh OH", throwable)
                );

        employeeKafkaTemplate.flush();

        createConsumerStream();
    }

    private void createConsumerStream(){
        streamsBuilder
                .stream("INPUT_1", Consumed.with(Serdes.Long(), StreamSerdes.employeeJsonSerde()))
                .peek((key, val) ->
                        {
                            if(val instanceof Programmer) {
                                log.info("Programmer 1 - {}", val);
                                p1 = (Programmer) val;
                            } else{
                                log.info("Employee 1 - {}", val);
                                e1 =val;
                            }
                        }
                )
                .to("INPUT_2", Produced.with(Serdes.Long(), StreamSerdes.employeeJsonSerde()));
        streamsBuilder
                .stream("INPUT_2", Consumed.with(Serdes.Long(), StreamSerdes.employeeJsonSerde()))
                .peek((key, val) ->
                        {
                            if(val instanceof Programmer) {
                                log.info("Programmer 2 - {}", val);
                                p2 = (Programmer) val;
                            } else{
                                log.info("Employee 2 - {}", val);
                                e2 = val;
                            }
                        }
                );


        streamsBuilder
                .stream("INPUT_3", Consumed.with(Serdes.Long(), StreamSerdes.customSerde()))
                .peek((key, val) ->
                        {
                            if(val instanceof Programmer) {
                                log.info("Programmer 11 - {}", val);
                                p11 = (Programmer) val;
                            } else{
                                log.info("Employee 11 - {}", val);
                                e11 = val;
                            }
                        }
                )
                .to("INPUT_4", Produced.with(Serdes.Long(), StreamSerdes.customSerde()));
        streamsBuilder
                .stream("INPUT_4", Consumed.with(Serdes.Long(), StreamSerdes.customSerde()))
                .peek((key, val) ->
                        {
                            if(val instanceof Programmer) {
                                log.info("Programmer 22 - {}", val);
                                p22 = (Programmer) val;
                            } else{
                                log.info("Employee 22 - {}", val);
                                e22 = val;
                            }
                        }
                );

    }
}
