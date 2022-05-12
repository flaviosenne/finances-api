package com.project.finances.domain.usecases.release.repository;

import com.project.finances.domain.entity.Release;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void delete(String id, String userId){
        repository.deleteByIdAndByUserId(id, userId);
    }
}
