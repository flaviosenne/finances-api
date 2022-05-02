package com.project.finances.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum StatusInvite {

    ACCEPT("Aceito"), REFUSED("Recusado"), PENDING("Pendente");

    private static final HashMap<String, StatusInvite> map = new HashMap<>();

    private String description;

    static {
        for(StatusInvite status: values()){
            map.put(status.name(), status);
        }
    }

    public static StatusInvite getStatus(String statusName){
        return map.get(statusName);
    }

    public static String getDescription(String statusName){
        return map.get(statusName).getDescription();
    }

}
