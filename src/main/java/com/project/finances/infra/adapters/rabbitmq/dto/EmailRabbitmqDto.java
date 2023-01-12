package com.project.finances.infra.adapters.rabbitmq.dto;

import com.project.finances.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailRabbitmqDto {

    private User user;

    private String code;
}
