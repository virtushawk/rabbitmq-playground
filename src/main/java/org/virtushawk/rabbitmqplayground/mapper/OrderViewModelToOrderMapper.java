package org.virtushawk.rabbitmqplayground.mapper;

import org.mapstruct.Mapper;
import org.virtushawk.rabbitmqplayground.entity.Order;
import org.virtushawk.rabbitmqplayground.entity.view.OrderViewModel;
import org.virtushawk.rabbitmqplayground.mapper.common.DTOToEntityMapper;
import org.virtushawk.rabbitmqplayground.mapper.common.MapperConfiguration;

/**
 *  Map {@link OrderViewModel} to {@link Order}
 */
@Mapper(config = MapperConfiguration.class, uses = ReceiptViewModelToReceiptMapper.class)
public interface OrderViewModelToOrderMapper extends DTOToEntityMapper<OrderViewModel, Order> {

}
