package in.net.sampleapp.controller;

import lombok.extern.slf4j.Slf4j;
import net.uidai.core.eventmodels.WorkflowEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class MessageController {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topic}")
    private String topic;

    public MessageController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/produce/{message}")
    public void produceMessage(@PathVariable("message") final String message) {
        WorkflowEvent workflowEvent =  WorkflowEvent.newBuilder()
                .setRefId(UUID.randomUUID().toString())
                .setSid("SRN997230720372323")
                .setBitmapIndex("121231231123123")
                        .setToa("qwrqwrqw")
                                .setUid("312341234142")
                                        .build();


        log.info(workflowEvent.toString());

        //kafkaTemplate.send(topic, message);
        // FlowMetaData flowMetaData = new FlowMetaData();
//        flowMetaData.set_event_id(UUID.randomUUID().toString());
//        flowMetaData.set_stage("TEST_STAGE");
//        flowMetaData.set_subStage("TEST_SUB_STAGE");

        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic,workflowEvent.getRefId(), workflowEvent);
//        // Skipping Header formulation
        kafkaTemplate.send(producerRecord);
    }
}
