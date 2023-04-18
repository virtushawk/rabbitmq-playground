package org.virtushawk.rabbitmqplayground.service;

import org.virtushawk.rabbitmqplayground.entity.BaseEntity;

import java.util.List;

/**
 * Contains common logic for all services
 *
 * @param <T> the type parameter
 */
public interface AbstractService <T extends BaseEntity> {

    /**
     * Find all
     *
     * @return the list of entities
     */
    List<T> findAll();

}
