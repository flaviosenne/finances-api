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

import static com.project.finances.domain.exception.messages.MessagesException.*;


@Service
@RequiredArgsConstructor
public class FlowCash implements FlowCashProtocol {

    private final ReleaseCommand command;
    private final UserQuery userQuery;
    private final CategoryQuery categoryQuery;
    private final ReleaseQuery query;

    @Override
    public ReleaseDto createRelease(ReleaseDto dto, String userId) {

        User user = userQuery.findByIdIsActive(userId)
                .orElseThrow(()-> new BadRequestException(USER_NOT_FOUND));

        Category category = categoryQuery.getCategoryByIdAndByUserId(dto.getCategory().getId(), user.getId())
                .orElseThrow(()-> new BadRequestException(CASH_FLOW_CATEGORY_NOT_PROVIDER));

        Release releaseToSave = ReleaseDto.of(dto)
                .withCategory(category)
                .withUser(user);

        return ReleaseDto.of(command.create(releaseToSave));
    }

    @Override
    public Page<Release> listReleases(String userId, Specification specification, Pageable pageable) {
        return query.getReleases(userId, specification, pageable);
    }

    @Override
    public ReleaseDto updateRelease(ReleaseDto dto, String userId) {

        Release entity = query.findReleaseById(dto.getId(), userId)
                .orElseThrow(()-> new BadRequestException(CASH_FLOW_NOT_FOUND));

        User user = userQuery.findByIdIsActive(userId)
                .orElseThrow(()-> new BadRequestException(USER_NOT_FOUND));

        Category category = categoryQuery.getCategoryByIdAndByUserId(dto.getCategory().getId(), userId)
                .orElseThrow(()-> new BadRequestException(CASH_FLOW_CATEGORY_NOT_PROVIDER));

        Release releaseToUpdate = ReleaseDto.of(dto)
                .withCategory(category)
                .withUser(user);

        return ReleaseDto.of(command.update(releaseToUpdate, entity.getId()));
    }

    @Override
    public void deleteRelease(String id, String userId){
        Release entity = query.findReleaseById(id, userId)
                .orElseThrow(()-> new BadRequestException(CASH_FLOW_NOT_FOUND));

        User user = userQuery.findByIdIsActive(userId)
                .orElseThrow(()-> new BadRequestException(USER_NOT_FOUND));

        command.delete(entity.getId(), user.getId());

    }

    public void sharedReleases(){

    }
}
