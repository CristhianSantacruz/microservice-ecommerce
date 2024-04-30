package com.coment.service;

import com.coment.model.CommentEntity;
import com.coment.utils.CommentType;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CommentService  implements ICommentService {


    @Override
    public CommentEntity saveComment(CommentEntity comment) {
        int calificationComment = comment.getCalification();
        if(comment.getContent().contains("?")) comment.setCommentType(CommentType.QUESTION);
        if(calificationComment>=3){
            comment.setCommentType(CommentType.POSITIVE);
        }else {
            comment.setCommentType(CommentType.NEGATIVE);
        }
        comment.setCreatedAt(LocalDateTime.now());
        comment.persist();
        return comment;
    }
    @Override
    public List<CommentEntity> getAllComments() {
        return CommentEntity.listAll();
    }

    @Override
    public List<CommentEntity> getAllCommentsByUser(String emailUser) {
        return CommentEntity.list("emailUser", emailUser);
    }


    @Override
    public List<CommentEntity> getAllCommentByIdProduct(String idProduct) {
        return CommentEntity.list("idProduct",idProduct) ;
    }

    @Override
    public Optional<CommentEntity> updateComment(ObjectId commentId, CommentEntity comment) {
        Optional<CommentEntity> optionalComment = CommentEntity.findByIdOptional(commentId);
        if(optionalComment.isPresent()){
            CommentEntity commentEntity = optionalComment.get();
            commentEntity.setContent(comment.getContent());
            commentEntity.setCalification(comment.getCalification());
            commentEntity.setVote(comment.getVote());
            commentEntity.setCommentType(comment.getCommentType());
            commentEntity.update();
            return Optional.of(commentEntity);
        }
        return Optional.empty();
    }




    @Override
    public boolean deleteComment(ObjectId idComment) {
        return CommentEntity.deleteById(idComment);
    }

    @Override
    public boolean updateCalification(ObjectId idComment,int calification) {
        Optional<CommentEntity> optionalComment = CommentEntity.findByIdOptional(idComment);
        if(optionalComment.isPresent()){
            CommentEntity commentEntity = optionalComment.get();
            commentEntity.setCalification(calification);
            commentEntity.update();
            return true;
        }
        return false;
    }

    @Override
    public boolean voteComment(ObjectId idComment) {
        Optional<CommentEntity> optionalComment = CommentEntity.findByIdOptional(idComment);
        if(optionalComment.isPresent()){
            CommentEntity commentEntity = optionalComment.get();
            commentEntity.setVote(commentEntity.getVote()+1);
            commentEntity.update();
            return true;
        }
        return false;
    }
}
