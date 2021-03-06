package ru.itmo.blss1.config.kafka;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka", ignoreUnknownFields = false)
public class KafkaProperty {
    private String server;
    private String groupId;
}