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

    /**
     * Create entity
     *
     * @param entity the entity
     * @return managed entity
     */
    T create(T entity);

    /**
     * Delete entity
     *
     * @param entity the entity
     */
    void delete(T entity);

    /**
     * Update entity
     *
     * @param entity the entity
     * @return updated entity
     */
    T update(T entity);

}
