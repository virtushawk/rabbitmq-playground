package org.virtushawk.rabbitmqplayground.mapper.common;

import org.virtushawk.rabbitmqplayground.entity.BaseDTO;
import org.virtushawk.rabbitmqplayground.entity.BaseEntity;

import java.util.List;

/**
 * Base mapper for entity to DTO relations
 *
 * @param <E> entity
 * @param <D> DTO
 */
public interface EntityToDTOMapper <E extends BaseEntity, D extends BaseDTO> {

    /**
     * Map to DTO
     *
     * @param source the entity
     * @return the DTO
     */
    D mapToDTO(E source);

    /**
     * Map to DTO list
     *
     * @param sourceList the entity list
     * @return the DTO list
     */
    List<D> mapToListDTO(List<E> sourceList);
}
