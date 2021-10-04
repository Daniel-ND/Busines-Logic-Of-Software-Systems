package ru.itmo.blss1.config;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.blss1.config.kafka.KafkaProperty;

import java.util.Properties;

@Configuration
@AllArgsConstructor
public class KafkaConfig {
    public static final String TOPIC = "add-pin";
    private final KafkaProperty kafkaProperty;

    @Bean
    public Producer<String, String> kafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProperty.getServer());
        props.put("acks", "all");
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    @Bean
    public Consumer<String, String> kafkaConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProperty.getServer());
        props.setProperty("group.id", kafkaProperty.getGroupId());
        props.setProperty("enable.auto.commit", "true");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<>(props);
    }
}
