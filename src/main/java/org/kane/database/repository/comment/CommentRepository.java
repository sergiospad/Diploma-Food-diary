package org.kane.database.repository.comment;

import org.kane.database.entity.recipe_recource.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {
}
