package org.virtushawk.rabbitmqplayground.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.virtushawk.rabbitmqplayground.entity.SalesItem;

/**
 * DAO for {@link SalesItem}
 */
public interface SalesItemDAO extends JpaRepository<SalesItem, String> {
    
}
