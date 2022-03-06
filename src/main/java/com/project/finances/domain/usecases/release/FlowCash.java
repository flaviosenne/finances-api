package com.project.finances.domain.usecases.release;

import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.protocols.FlowCashProtocol;
import com.project.finances.domain.usecases.release.dto.ReleaseDto;
import com.project.finances.domain.usecases.release.repository.ReleaseCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlowCash implements FlowCashProtocol {

    private final ReleaseCommand releaseCommand;

    @Override
    public ReleaseDto createRelease(ReleaseDto dto) {

        User user = User.builder().build();

        Category category = new Category("", user);

        Release releaseToSave = ReleaseDto.of(dto)
                .withCategory(category)
                .withUser(user);

        Release releaseSaved = releaseCommand.create(releaseToSave);

        return ReleaseDto.of(releaseSaved);
    }
}
