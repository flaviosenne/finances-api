package com.project.finances.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BasicEntity implements Serializable {

    private static final long serialVersionUID = 2739706603628858379L;

    @Id
    protected String id = UUID.randomUUID().toString();

    @Column(name = "created_at")
    @CreatedDate
    protected Date createdAt = new Date();

    @Column(name = "updated_at")
    @CreatedDate
    protected Date updatedAt = new Date();
}
