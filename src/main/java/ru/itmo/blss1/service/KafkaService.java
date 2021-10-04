package ru.itmo.blss1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.itmo.blss1.data.dto.PinDTO;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static ru.itmo.blss1.config.KafkaConfig.TOPIC;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaService {
    private final Consumer<String, String> consumer;
    private final ObjectMapper objectMapper;
    private final PinService pinService;
    private final Producer<String, String> producer;

    @EventListener
    public void receive(ContextRefreshedEvent event ) throws JsonProcessingException {
        consumer.subscribe(Collections.singleton(TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                log.info("Receive {} with offset {}", record.value(), record.offset());
                final PinDTO pinDto = objectMapper.readValue(record.value(), PinDTO.class);
                pinService.newPin(pinDto);
            }
        }
    }

    public void send(Object obj) throws JsonProcessingException {
        final String msg = objectMapper.writeValueAsString(obj);
        try {
            final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, msg);
            RecordMetadata metadata = producer.send(record).get();
            log.info("sent record {} with offset {}", record.value(), metadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }
}
