package org.virtushawk.rabbitmqplayground.mapper;

import org.mapstruct.Mapper;
import org.virtushawk.rabbitmqplayground.entity.Receipt;
import org.virtushawk.rabbitmqplayground.entity.view.ReceiptViewModel;
import org.virtushawk.rabbitmqplayground.mapper.common.DTOToEntityMapper;
import org.virtushawk.rabbitmqplayground.mapper.common.MapperConfiguration;

/**
 *  Map {@link ReceiptViewModel} to {@link Receipt}
 */
@Mapper(config = MapperConfiguration.class, uses = SalesItemViewModelToSalesItemMapper.class)
public interface ReceiptViewModelToReceiptMapper extends DTOToEntityMapper<ReceiptViewModel, Receipt> {
}
