package org.virtushawk.rabbitmqplayground.mapper.common;

import org.virtushawk.rabbitmqplayground.entity.BaseDTO;
import org.virtushawk.rabbitmqplayground.entity.BaseEntity;

import java.util.List;

/**
 * Base mapper for DTO to entity relations
 *
 * @param <D> DTO
 * @param <E> Entity
 */
public interface DTOToEntityMapper <D extends BaseDTO, E extends BaseEntity> {

    /**
     * Map to Entity
     *
     * @param dto the DTO
     * @return the Entity
     */
    E mapToEntity(D dto);

    /**
     * Map to Entity list
     *
     * @param dtoList the DTO list
     * @return the Entity list
     */
    List<E> mapToEntityList(List<D> dtoList);
}
