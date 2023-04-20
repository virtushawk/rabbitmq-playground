package org.virtushawk.rabbitmqplayground.mapper;

import org.mapstruct.Mapper;
import org.virtushawk.rabbitmqplayground.entity.Order;
import org.virtushawk.rabbitmqplayground.entity.view.OrderViewModel;
import org.virtushawk.rabbitmqplayground.mapper.common.EntityToDTOMapper;
import org.virtushawk.rabbitmqplayground.mapper.common.MapperConfiguration;

/**
 * Map {@link Order} to {@link OrderViewModel}
 */
@Mapper(config = MapperConfiguration.class, uses = ReceiptToReceiptViewModelMapper.class)
public interface OrderToOrderViewModelMapper extends EntityToDTOMapper<Order, OrderViewModel> {

}
