/**
 * @package Quarkus-Kafka-Showcase
 *
 * @file Todo resource
 * @copyright 2020-present Christoph Kappel <christoph@unexist.dev>
 * @version $Id$
 *
 * This program can be distributed under the terms of the Apache License v2.0. See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.adapter;

import dev.unexist.showcase.todo.domain.todo.Todo;
import dev.unexist.showcase.todo.generated.avro.Todov1;
import dev.unexist.showcase.todo.generated.avro.Todov2;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.apache.avro.specific.SpecificRecord;
import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class TodoGenerator {
    private static final int SECONDS = 30;
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoGenerator.class);

    private final Random random = new Random();

    private List<Todo> todos = List.of(
            new Todo("First", "Bla"),
            new Todo("Second", "Bla-bla"),
            new Todo("Third", "Bla-bla-bla"),
            new Todo("Fourth", "Bla-bla-bla-bla"),
            new Todo("Fifth", "Bla-bla-bla-bla-bla"),
            new Todo("Sixth", "Bla-bla-bla-bla-bla-bla"));

    @Outgoing("todo-generator")
    public Multi<KafkaRecord<Integer, SpecificRecord>> generate() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(SECONDS))
                .onOverflow().drop()
                .map(tick -> {
                    int idx = random.nextInt(this.todos.size());
                    Todo todo = todos.get(idx);

                    if (0 == idx % 2) {
                        Todov1 todov1 = Todov1.newBuilder()
                            .setTitle(todo.getTitle())
                            .setDescription(todo.getDescription())
                            .build();

                        LOGGER.info("Send v1: idx={}", idx);

                        return KafkaRecord.of(idx, todov1);
                    } else {
                        Todov2 todov2 = Todov2.newBuilder()
                            .setTitle(todo.getTitle())
                            .setDescription(todo.getDescription())
                            .setDone(BooleanUtils.isTrue(todo.getDone()))
                            .build();

                        LOGGER.info("Send v2: idx={}", idx);

                        return KafkaRecord.of(idx, todov2);
                    }
                });
    }
}
