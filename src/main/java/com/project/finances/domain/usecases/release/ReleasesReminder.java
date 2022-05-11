package com.project.finances.domain.usecases.release;

import com.project.finances.domain.entity.Release;
import com.project.finances.domain.protocols.ReleaseReminderProtocol;
import com.project.finances.domain.usecases.release.repository.ReleaseQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleasesReminder implements ReleaseReminderProtocol {

    private final ReleaseQuery query;
    @Override
    public List<Release> listReleasesReminder(String userId) {
        return query.findReleasesCloseExpiration(userId);
    }
}
