package com.coment.service;

import com.coment.client.UserClient;
import com.coment.dto.CommentDto;
import com.coment.dto.UserDto;
import com.coment.model.CommentEntity;
import com.coment.utils.CommentType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CommentService  implements ICommentService {

    @Inject
    @RestClient
    UserClient userClient;


    @Override
    public CommentEntity saveComment(CommentDto comment) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setIdProduct(comment.idProduct());
        commentEntity.setContent(comment.content());
        commentEntity.setCalification(comment.calification());
        UserDto userByEmail = userClient.getUserByEmail(comment.email());
        commentEntity.setOwner(userByEmail);
        if(commentEntity.getContent().contains("?")) commentEntity.setCommentType(CommentType.QUESTION);
        if(commentEntity.getCalification()>=3){
            commentEntity.setCommentType(CommentType.POSITIVE);
        }else {
            commentEntity.setCommentType(CommentType.NEGATIVE);
        }
        commentEntity.setCreatedAt(LocalDateTime.now());
        commentEntity.persist();
        return commentEntity;
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
