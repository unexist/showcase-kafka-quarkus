/**
 * @package Quarkus-Kafka-Showcase
 *
 * @file Todo class and aggregate root
 * @copyright 2020-present Christoph Kappel <christoph@unexist.dev>
 * @version $Id$
 *
 * This program can be distributed under the terms of the Apache License v2.0.
 * See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.domain.todo;

public class Todo extends TodoBase {
    private int id;

    /**
     * Constructor
     **/

    public Todo() {
    }

    /**
     * Constructor
     *
     * @param  title        Title of the entry
     * @param  description  Description of the entry
     **/

    public Todo(final String title, final String description) {
        this.setTitle(title);
        this.setDescription(description);
    }

    /**
     * Constructor
     *
     * @param  base  Base entry
     **/

    public Todo(final TodoBase base) {
        this.update(base);
    }

    /**
     * Update values from base
     *
     * @param  base  Todo base class
     **/

    public void update(final TodoBase base) {
        this.setTitle(base.getTitle());
        this.setDescription(base.getDescription());
        this.setDone(base.getDone());

        if (null != base.getDueDate()) {
            this.setDueDate(base.getDueDate());
        }
    }

    /**
     * Get id of entry
     *
     * @return Id of the entry
     **/

    public int getId() {
        return id;
    }

    /**
     * Set id of entry
     *
     * @param  id  Id of the entry
     **/

    public void setId(int id) {
        this.id = id;
    }
}
