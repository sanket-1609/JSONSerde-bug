Repo for JsonSerde bug. 

There is and employee and Programme(subclass of Employee)
Flow -
1. send via a kafka template using jsonserializer to a topic INPUT_1/INPUT_3
2. INPUT1/3 are read by kafka streams using JsonDeserialzer in JsonSerde/CustomSerde (Both work fine till here). They store the employee/programmer in the static variables e1/e11/p1/p11
3. Data is sent from INPUT1/3 to INPUT2/4 using JsonSerializer in JsonSerde and CustomSerializer(check for type and then uses the relevant jsonSerializer) in CustomSerde
4. INPUT2/4 are read by kafka streams using JsonDeserialzer in JsonSerde/CustomSerde. They store the employee/programmer in the static variables e2/e22/p2/p22
p2 doesn't have the relevant field(preferredCodingLanguage) as in 3 the JsonSerializer serializes it as Employee instead of programmer.
This behaviour is deifferent from vanilla KafkaTemplate which serializes is correct (as p1 is fine)

All this is caputed in test DoodleApplicationTest
