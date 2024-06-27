package com.eeumteo.eeumteo_backend.post.entity;

import com.eeumteo.eeumteo_backend.base.BaseEntity;
import com.eeumteo.eeumteo_backend.board.entity.Board;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Board {


}
