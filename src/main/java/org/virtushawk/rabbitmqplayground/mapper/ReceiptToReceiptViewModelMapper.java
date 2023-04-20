package org.virtushawk.rabbitmqplayground.mapper;

import org.mapstruct.Mapper;
import org.virtushawk.rabbitmqplayground.entity.Receipt;
import org.virtushawk.rabbitmqplayground.entity.view.ReceiptViewModel;
import org.virtushawk.rabbitmqplayground.mapper.common.EntityToDTOMapper;
import org.virtushawk.rabbitmqplayground.mapper.common.MapperConfiguration;

/**
 * Map {@link Receipt} to {@link ReceiptViewModel}
 */
@Mapper(config = MapperConfiguration.class, uses = SalesItemToSalesItemViewModelMapper.class)
public interface ReceiptToReceiptViewModelMapper extends EntityToDTOMapper<Receipt, ReceiptViewModel>{
}
