package com.coment.model;

import com.coment.utils.CommentType;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@MongoEntity(collection = "comment")
public class CommentEntity extends PanacheMongoEntity {

    //idProduct
    private String idProduct;
    @NotNull @NotBlank
    private String content;
    private int calification;
    private String emailUser;
    private int vote;
    private CommentType commentType;
    private LocalDateTime createdAt;

    public String getIdProduct() {
        return idProduct;
    }

    public String getNameUser() {
        return emailUser;
    }

    public void setNameUser(String nameUser) {
        this.emailUser = nameUser;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCalification() {
        return calification;
    }

    public void setCalification(int calification) {
        this.calification = calification;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public CommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
