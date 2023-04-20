package org.virtushawk.rabbitmqplayground.service;

import org.springframework.stereotype.Service;
import org.virtushawk.rabbitmqplayground.dao.ArticleDAO;
import org.virtushawk.rabbitmqplayground.entity.Article;

import java.util.List;

/**
 * Implementation of {@link ArticleService}
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDAO articleDAO;

    public ArticleServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public List<Article> findAll() {
        return articleDAO.findAll();
    }

    @Override
    public Article create(Article entity) {
        return articleDAO.save(entity);
    }

    @Override
    public void delete(Article entity) {
        articleDAO.delete(entity);
    }

    @Override
    public Article update(Article entity) {
        return articleDAO.save(entity);
    }
}
