package com.project.finances.mocks.entity;

import com.project.finances.domain.entity.Category;

public class CategoryMock {
    public static Category get(){
        return new Category(null, "category 1", true, UserMock.get());
    }
}
