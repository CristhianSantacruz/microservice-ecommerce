package com.coment.service;

import com.coment.dto.CommentDto;
import com.coment.model.CommentEntity;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface ICommentService {

    List<CommentEntity> getAllComments();
    List<CommentEntity> getAllCommentsByUser(String emailUser);
    List<CommentEntity> getAllCommentByIdProduct(String idProduct);

    Optional<CommentEntity> updateComment(ObjectId commentId, CommentEntity comment);
    CommentEntity saveComment(CommentDto commentDto);
    boolean deleteComment(ObjectId idComment);
    boolean updateCalification(ObjectId objectId,int idCalification);
    boolean voteComment(ObjectId idComment);
}
