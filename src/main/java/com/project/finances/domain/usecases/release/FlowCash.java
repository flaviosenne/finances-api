package com.project.finances.domain.usecases.release;

import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.FlowCashProtocol;
import com.project.finances.domain.usecases.category.repository.CategoryQuery;
import com.project.finances.domain.usecases.release.dto.ReleaseDto;
import com.project.finances.domain.usecases.release.repository.ReleaseCommand;
import com.project.finances.domain.usecases.release.repository.ReleaseQuery;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.project.finances.domain.exception.messages.MessagesException.CASH_FLOW_CATEGORY_NOT_PROVIDER;
import static com.project.finances.domain.exception.messages.MessagesException.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class FlowCash implements FlowCashProtocol {

    private final ReleaseCommand command;
    private final UserQuery userQuery;
    private final CategoryQuery categoryQuery;
    private final ReleaseQuery query;
    @Override
    public ReleaseDto createRelease(ReleaseDto dto, String userId) {

        User user = userQuery.findByIdIsActive(userId).orElseThrow(()-> new BadRequestException(USER_NOT_FOUND));

        Category category = categoryQuery.getCategoryByIdAndByUserId(dto.getCategory().getId(), user.getId()).orElseThrow(()-> new BadRequestException(CASH_FLOW_CATEGORY_NOT_PROVIDER));

        Release releaseToSave = ReleaseDto.of(dto)
                .withCategory(category)
                .withUser(user);

        Release releaseSaved = command.create(releaseToSave);

        return ReleaseDto.of(releaseSaved);
    }

    @Override
    public Page<Release> listReleases(String userId, Specification specification, Pageable pageable) {
        return query.getReleases(userId, specification, pageable);
    }
}
