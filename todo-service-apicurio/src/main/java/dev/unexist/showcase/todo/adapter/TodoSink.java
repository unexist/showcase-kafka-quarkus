/**
 * @package Quarkus-Kafka-Showcase
 *
 * @file Todo sink
 * @copyright 2020-2021 Christoph Kappel <christoph@unexist.dev>
 * @version $Id$
 *
 * This program can be distributed under the terms of the GNU GPLv3. See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.adapter;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.apache.avro.specific.SpecificRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class TodoSink {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoGenerator.class);

    @Incoming("todo-sink")
    public CompletionStage<Void> receive(KafkaRecord<Integer, SpecificRecord> message) {
        return CompletableFuture.runAsync(() -> {
            LOGGER.error("Read: {}: {}", message.getPayload().getSchema().getFullName(), message.getPayload());

            message.ack();
        });
    }
}