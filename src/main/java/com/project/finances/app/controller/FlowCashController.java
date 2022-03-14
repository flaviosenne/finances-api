package com.project.finances.app.controller;

import com.project.finances.app.utils.PageGenerics;
import com.project.finances.app.vo.release.ListReleasesVo;
import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.protocols.FlowCashProtocol;
import com.project.finances.domain.usecases.release.dto.ReleaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;


@RestController
@RequestMapping("/v1/releases")
@RequiredArgsConstructor
public class FlowCashController {

    private final FlowCashProtocol flowCashProtocol;

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ReleaseDto dto){
        flowCashProtocol.createRelease(dto);
    }

    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageGenerics<ListReleasesVo> listReleases(@AuthenticationPrincipal User user,
                                                     @RequestParam Specification<Release> search,
                                                     @SortDefault.SortDefaults({ @SortDefault(sort = "createdAt", direction = DESC) }) Pageable pageable){
        PageImpl<ListReleasesVo> resultPageable = new PageImpl<ListReleasesVo>(flowCashProtocol
                .listReleases(user.getId(), search, pageable).getContent()
                .stream().map(ListReleasesVo::of)
                .collect(Collectors.toList()));

        return new PageGenerics<ListReleasesVo>(resultPageable.getTotalElements(),
                pageable.getPageSize(), pageable.getPageNumber(),
                resultPageable.isLast(), resultPageable.getContent());
    }
}
