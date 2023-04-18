package org.virtushawk.rabbitmqplayground.service;

import org.springframework.stereotype.Service;
import org.virtushawk.rabbitmqplayground.dao.SalesItemDAO;
import org.virtushawk.rabbitmqplayground.entity.SalesItem;

import java.util.List;

/**
 * Implementation of {@link SalesItemService}
 */
@Service
public class SalesItemServiceImpl implements SalesItemService {

    private final SalesItemDAO salesItemDAO;

    public SalesItemServiceImpl(SalesItemDAO salesItemDAO) {
        this.salesItemDAO = salesItemDAO;
    }

    @Override
    public List<SalesItem> findAll() {
        return salesItemDAO.findAll();
    }
}
