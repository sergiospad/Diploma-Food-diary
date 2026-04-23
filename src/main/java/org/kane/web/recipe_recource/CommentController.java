package org.kane.web.recipe_recource;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.database.entity.recipe_recource.Comment;
import org.kane.domain.DTO.entityDTO.recipe_recource.comment.CommentCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.comment.CommentShowDTO;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.recipe_recource.comment.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PutMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommentShowDTO> createComment(Principal principal, @RequestBody CommentCreateDTO comment) {
        var result = commentService.createComment(principal, comment);
        return ResponseEntity.ok().body(result);
    }

    /**
     * DELETE /api/comment/delete?id=1
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> deleteComment(Principal principal, @RequestParam("id") Long id) {
        commentService.deleteComment(principal, id);
        return ResponseEntity.ok(new MessageResponse("Comment has been deleted"));
    }

}
