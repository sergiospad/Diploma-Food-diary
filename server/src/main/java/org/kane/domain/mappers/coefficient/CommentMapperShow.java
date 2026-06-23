package org.kane.domain.mappers.coefficient;

import org.kane.database.entity.recipe_recource.Comment;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.domain.DTO.entityDTO.recipe_recource.comment.CommentShowDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommentMapperShow implements Mapper<Comment, CommentShowDTO> {
    @Override
    public CommentShowDTO map(Comment from) {
        return CommentShowDTO.builder()
                .id(from.getId())
                .username(from.getCommentator().getUsername())
                .userAvatarID(from.getCommentator().getAvatar().getId())
                .message(from.getMessage())
                .createdAt(from.getCreatedAt())
                .imageID(Optional.ofNullable(from.getImage()).map(ImageModel::getId).orElse(null))
                .build();
    }
}
