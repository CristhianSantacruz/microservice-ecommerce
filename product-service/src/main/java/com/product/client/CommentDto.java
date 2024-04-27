package com.product.client;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class CommentDto {
    private ObjectId id;
    private String content;
    private int calification;
    private int vote;
    private String commentType;
    private LocalDateTime createdAt;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", calification=" + calification +
                ", vote=" + vote +
                ", commentType='" + commentType + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
