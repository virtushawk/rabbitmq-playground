package org.virtushawk.rabbitmqplayground.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.virtushawk.rabbitmqplayground.entity.Article;

/**
 * DAO for {@link Article}
 */
public interface ArticleDAO extends JpaRepository<Article, String> {
    
}
