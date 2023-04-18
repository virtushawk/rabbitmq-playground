package org.virtushawk.rabbitmqplayground.mapper;

import org.mapstruct.Mapper;
import org.virtushawk.rabbitmqplayground.entity.SalesItem;
import org.virtushawk.rabbitmqplayground.entity.view.SalesItemViewModel;
import org.virtushawk.rabbitmqplayground.mapper.common.EntityToDTOMapper;
import org.virtushawk.rabbitmqplayground.mapper.common.MapperConfiguration;

/**
 * Map {@link SalesItem} to {@link SalesItemViewModel}
 */
@Mapper(config = MapperConfiguration.class)
public interface SalesItemToSalesItemViewModelMapper extends EntityToDTOMapper<SalesItem, SalesItemViewModel> {
}
