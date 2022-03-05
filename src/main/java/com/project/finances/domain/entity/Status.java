package com.project.finances.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum Status {

    PENDING("Pendente"), PAID("Pago"), CANCEL("Cancelado");

    private static final HashMap<String, Status> map = new HashMap<>();

    private String description;

    static {
        for(Status status: values()){
            map.put(status.name(), status);
        }
    }

    public String getStatus(String statusName){
        return map.get(statusName).getDescription();
    }
}
