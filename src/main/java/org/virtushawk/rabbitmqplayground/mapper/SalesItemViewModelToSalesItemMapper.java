package org.virtushawk.rabbitmqplayground.mapper;

import org.mapstruct.Mapper;
import org.virtushawk.rabbitmqplayground.entity.SalesItem;
import org.virtushawk.rabbitmqplayground.entity.view.SalesItemViewModel;
import org.virtushawk.rabbitmqplayground.mapper.common.DTOToEntityMapper;
import org.virtushawk.rabbitmqplayground.mapper.common.MapperConfiguration;

/**
 *  Map {@link SalesItemViewModel} to {@link SalesItem}
 */
@Mapper(config = MapperConfiguration.class)
public interface SalesItemViewModelToSalesItemMapper extends DTOToEntityMapper<SalesItemViewModel, SalesItem> {
}
