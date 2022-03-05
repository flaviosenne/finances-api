package com.project.finances.domain.usecases.release;

import com.project.finances.domain.entity.Release;
import com.project.finances.domain.protocols.ReleaseProtocol;
import com.project.finances.domain.usecases.release.repository.ReleaseCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Releases implements ReleaseProtocol {

    private final ReleaseCommand releaseCommand;

    @Override
    public Release createRelease(Release release) {
        return releaseCommand.create(release);
    }
}
