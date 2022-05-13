package com.project.finances.domain.protocols.usecases;

import com.project.finances.domain.entity.Release;

import java.util.List;


public interface ReleaseReminderProtocol {
    List<Release> listReleasesReminder(String userId);
}
