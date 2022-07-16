package com.project.finances.infra.adapters.repositories.release;

import com.project.finances.app.usecases.release.repository.ReleaseRepository;
import com.project.finances.domain.entity.Release;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.finances.infra.adapters.repositories.release.ReleaseSpecification.listReleaseByUserId;

@Service
@AllArgsConstructor
public class ReleaseRepositoryJpa implements ReleaseRepository {

    private final ReleaseInterfaceJpa jpa;

    @Override
    public Page<Release> findAllByUser(String userId, Pageable pageable) {
        return jpa.findAllByUser(userId, pageable);
    }

    @Override
    public Optional<Release> findByIdAndByUserId(String id, String userId) {
        return jpa.findByIdAndByUserId(id, userId);
    }

    @Override
    public Optional<Release> findOneReleaseByIdAndByUserIdToDelete(String id, String userId) {
        return jpa.findOneReleaseByIdAndByUserIdToDelete(id, userId);
    }

    @Override
    public List<Release> findReleasesCloseExpirationIn5Days(String userId, Date today) {
        Date plus5Day = new Date(today.getTime() + 518400000);
        return jpa.findReleasesCloseExpirationIn5Days(userId, today, plus5Day);
    }

    @Override
    public List<Release> findAllByUserId(String userId, Pageable pageable) {
        return jpa.findAll(listReleaseByUserId(userId), pageable)
                .stream().filter(Release::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public Release save(Release release) {
        return jpa.save(release);
    }

    @Override
    public Double getBalanceTotal(String userId) {
        return jpa.getBalanceTotal(userId);
    }
}
