package com.project.finances.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum StatusRelease {

    PENDING("Pendente"), PAID("Pago"), CANCEL("Cancelado");

    private static final HashMap<String, StatusRelease> map = new HashMap<>();

    private String description;

    static {
        for(StatusRelease statusRelease : values()){
            map.put(statusRelease.name(), statusRelease);
        }
    }

    public static StatusRelease getStatus(String statusName){
        return map.get(statusName);
    }

    public static String getDescription(String statusName){
        return map.get(statusName).getDescription();
    }

}
