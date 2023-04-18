package org.virtushawk.rabbitmqplayground.mapper.common;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingInheritanceStrategy;

/**
 * Base configuration for all mappers
 */
@MapperConfig(
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG
)
public interface MapperConfiguration {
}
