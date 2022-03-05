package com.project.finances.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum Type {

    RECEP("Receita"), EXPENSE("Despesa");

    private static final HashMap<String, Type> map = new HashMap<>();

    private String description;

    static {
        for(Type type: values()){
            map.put(type.name(), type);
        }
    }

    public String getStatus(String statusName){
        return map.get(statusName).getDescription();
    }
}
