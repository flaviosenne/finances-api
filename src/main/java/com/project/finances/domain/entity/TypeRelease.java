package com.project.finances.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum TypeRelease {

    RECEP("Receita"), EXPENSE("Despesa");

    private static final HashMap<String, TypeRelease> map = new HashMap<>();

    private String description;

    static {
        for(TypeRelease typeRelease : values()){
            map.put(typeRelease.name(), typeRelease);
        }
    }

    public static TypeRelease getType(String typeName){
        return map.get(typeName);
    }

    public static String getDescription(String statusName){
        return map.get(statusName).getDescription();
    }
}
