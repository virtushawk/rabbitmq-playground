package org.virtushawk.rabbitmqplayground.mapper;

import org.mapstruct.Mapper;
import org.virtushawk.rabbitmqplayground.entity.Article;
import org.virtushawk.rabbitmqplayground.entity.view.ArticleViewModel;
import org.virtushawk.rabbitmqplayground.mapper.common.EntityToDTOMapper;
import org.virtushawk.rabbitmqplayground.mapper.common.MapperConfiguration;

/**
 * Map {@link Article} to {@link ArticleViewModel}
 */
@Mapper(config = MapperConfiguration.class)
public interface ArticleToArticleViewModelMapper extends EntityToDTOMapper<Article, ArticleViewModel> {

}
