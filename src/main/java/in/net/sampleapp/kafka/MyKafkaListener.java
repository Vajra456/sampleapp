package in.net.sampleapp.kafka;

import lombok.extern.slf4j.Slf4j;
import net.uidai.core.eventmodels.WorkflowEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyKafkaListener {

    @RetryableTopic(
            attempts = "5",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            exclude = {SerializationException.class, DeserializationException.class}
    )
    @KafkaListener(id = "${spring.kafka.consumer.group-id}", topics = "${topic}")
    //            properties = "value.deserializer=in.net.sampleapp.Serializers.WorkflowEventDeserializers")
    //        properties = "spring.json.value.default.type=in.net.sampleapp.model.event.WorkflowEvent")
    public void handleMessage(ConsumerRecord<String, WorkflowEvent> message, @Headers MessageHeaders headers) {
        log.info("Received message: {} from topic: {} partition: {}", message.getClass(), headers.get("topic"));
        //throw new RuntimeException("Test exception");
    }


    @DltHandler
    public void handleDlt(ConsumerRecord<String, WorkflowEvent>  message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Message: {} handled by dlq topic: {}", message, topic);
    }
}