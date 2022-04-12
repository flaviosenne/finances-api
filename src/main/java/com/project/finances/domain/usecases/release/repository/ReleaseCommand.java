package com.project.finances.domain.usecases.release.repository;

import com.project.finances.domain.entity.Release;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReleaseCommand {
    private final ReleaseRepository repository;

    public Release create(Release release){
        return repository.save(release);
    }

    public Release update(Release release, String id){
        return repository.save(release.withId(id));
    }
}
