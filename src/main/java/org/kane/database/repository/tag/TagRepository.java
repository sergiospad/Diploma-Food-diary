package org.kane.database.repository.tag;

import org.kane.database.entity.recipe_recource.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, CustomTagRepository {

    //TODO оптимизировать
    public Tag findById(long id);

}
