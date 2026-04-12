package org.kane.database.repository.tag;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.TagDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.QRecipe.recipe;
import static org.kane.database.entity.recipe_recource.QTag.tag;

@Repository
@RequiredArgsConstructor
public class CustomTagRepositoryImpl implements CustomTagRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<TagDTO> findAllTags(){
        return queryFactory.select(Projections.constructor(TagDTO.class,
                    tag.id,
                    tag.name))
                .from(tag)
                .fetch();
    }

    @Override
    public List<TagDTO> findAllTagsOfRecipe(Long recipeID){
        return queryFactory.select(Projections.constructor(TagDTO.class,
                tag.id,
                tag.name))
                .from(recipe)
                .join(recipe.tags, tag)
                .where(recipe.id.eq(recipeID))
                .fetch();
    }


}
