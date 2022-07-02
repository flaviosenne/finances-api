package com.project.finances.mocks.entity;

import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.StatusRelease;
import com.project.finances.domain.entity.TypeRelease;

import java.util.Date;

public class ReleaseMock {
    public static Release get(){
        return new Release(
                100d, "test", StatusRelease.PENDING.name(),
                TypeRelease.EXPENSE.name(), new Date(),
                CategoryMock.get(), UserMock.get(), true);
    }
}
