package com.infinnity.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinnity.kafka.domain.BookTableMessage;
import com.infinnity.kafka.events.TopicNameProvider;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.ToStringSerializer;

import static org.apache.kafka.common.config.TopicConfig.CLEANUP_POLICY_COMPACT;

@Configuration
public class KafkaConfig {

    private final KafkaProperties properties;
    private final TopicNameProvider topicNameProvider;
    private final ObjectMapper kafkaObjectMapper;

    public KafkaConfig(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") KafkaProperties properties,
                       TopicNameProvider topicNameProvider,
                       ObjectMapper kafkaObjectMapper) {
        this.properties = properties;
        this.topicNameProvider = topicNameProvider;
        this.kafkaObjectMapper = kafkaObjectMapper;
    }

    @Bean
    public NewTopic bookTableTopic() {
        return TopicBuilder.name(topicNameProvider.bookTableTopic())
                .partitions(3)
                .replicas(1)
                .config(TopicConfig.CLEANUP_POLICY_CONFIG, CLEANUP_POLICY_COMPACT)
                .build();
    }

    @Bean
    public KafkaTemplate<String, BookTableMessage> bookTableMessageKafkaTemplate() {
        return new KafkaTemplate<>(bookTableProducerFactory());
    }

    @Bean
    public ProducerFactory<String, BookTableMessage> bookTableProducerFactory() {
        var factory = new DefaultKafkaProducerFactory<String, BookTableMessage>(properties.buildProducerProperties());
        setupProducerFactory(factory);
        return factory;
    }

    private <K, V> void setupProducerFactory(DefaultKafkaProducerFactory<K, V> factory) {
//        var transactionIdPrefix = properties.getProducer().getTransactionIdPrefix();
//        if (transactionIdPrefix != null) {
//            factory.setTransactionIdPrefix(transactionIdPrefix);
//        }
        factory.setKeySerializer(new ToStringSerializer<>());
        factory.setValueSerializer(new JsonSerializer<>(kafkaObjectMapper));
    }
}
