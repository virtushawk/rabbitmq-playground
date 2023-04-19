package org.virtushawk.rabbitmqplayground.mapper;

import org.mapstruct.Mapper;
import org.virtushawk.rabbitmqplayground.entity.Article;
import org.virtushawk.rabbitmqplayground.entity.view.ArticleViewModel;
import org.virtushawk.rabbitmqplayground.mapper.common.DTOToEntityMapper;
import org.virtushawk.rabbitmqplayground.mapper.common.MapperConfiguration;

/**
 *  Map {@link ArticleViewModel} to {@link Article}
 */
@Mapper(config = MapperConfiguration.class)
public interface ArticleViewModelToArticleMapper extends DTOToEntityMapper<ArticleViewModel, Article> {
}
