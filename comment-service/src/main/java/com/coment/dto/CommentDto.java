package com.coment.dto;

import jakarta.validation.constraints.NotNull;

public record CommentDto(@NotNull String email, @NotNull  String idProduct,@NotNull String content,@NotNull int calification) {
}
