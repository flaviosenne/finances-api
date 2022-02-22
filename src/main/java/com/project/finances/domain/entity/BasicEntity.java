package com.project.finances.domain.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public class BasicEntity implements Serializable {

    private static final long serialVersionUID = 2739706603628858379L;

    @Id
    private final String id = UUID.randomUUID().toString();

    @Column(name = "created_at")
    @CreatedDate
    private final Date createdAt = new Date();

    @Column(name = "updated_at")
    @CreatedDate
    private final Date updatedAt = new Date();
}
