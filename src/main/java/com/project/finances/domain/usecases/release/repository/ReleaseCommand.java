package com.project.finances.domain.usecases.release.repository;

import com.project.finances.domain.entity.Release;
import com.project.finances.domain.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.finances.domain.exception.messages.MessagesException.CASH_FLOW_NOT_FOUND;

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

    public void delete(String id, String userId){
        Release releaseToDelete = repository.findOneReleaseByIdAndByUserIdToDelete(id, userId)
                .orElseThrow(()-> new BadRequestException(CASH_FLOW_NOT_FOUND));

        repository.save(releaseToDelete.disable());
    }
}
