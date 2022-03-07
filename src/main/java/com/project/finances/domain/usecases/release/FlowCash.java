package com.project.finances.domain.usecases.release;

import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.FlowCashProtocol;
import com.project.finances.domain.usecases.category.repository.CategoryQuery;
import com.project.finances.domain.usecases.release.dto.ReleaseDto;
import com.project.finances.domain.usecases.release.repository.ReleaseCommand;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlowCash implements FlowCashProtocol {

    private final ReleaseCommand releaseCommand;
    private final UserQuery userQuery;
    private CategoryQuery categoryQuery;

    @Override
    public ReleaseDto createRelease(ReleaseDto dto) {

        User user = userQuery.findByUsername(dto.getUser().getId()).orElseThrow(()-> new BadRequestException("Usuário não informado"));

        Category category = categoryQuery.getCategoryById(dto.getCategory().getId()).orElseThrow(()-> new BadRequestException("Categoria não informada"));

        Release releaseToSave = ReleaseDto.of(dto)
                .withCategory(category)
                .withUser(user);

        Release releaseSaved = releaseCommand.create(releaseToSave);

        return ReleaseDto.of(releaseSaved);
    }
}
