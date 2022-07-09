package com.project.finances.app.presentation.rest;

import com.project.finances.app.presentation.rest.utils.PageGenerics;
import com.project.finances.app.presentation.rest.vo.release.ListReleasesVo;
import com.project.finances.app.presentation.rest.vo.release.ReleaseBalanceVo;
import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.User;
import com.project.finances.app.usecases.release.FlowCashProtocol;
import com.project.finances.app.usecases.release.dto.ReleaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public void create(@RequestBody ReleaseDto dto, @AuthenticationPrincipal User user){
        flowCashProtocol.createRelease(dto, user.getId());
    }

    @CrossOrigin
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody ReleaseDto dto, @AuthenticationPrincipal User user){
        flowCashProtocol.updateRelease(dto, user.getId());
    }

    @CrossOrigin
    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@RequestParam("id") String id, @AuthenticationPrincipal User user){
        flowCashProtocol.updateStatusRelease(id, user.getId());
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id, @AuthenticationPrincipal User user){
        flowCashProtocol.deleteRelease(id, user.getId());
    }

    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageGenerics<ListReleasesVo> listReleases(@AuthenticationPrincipal User user,
                                                     @SortDefault.SortDefaults({ @SortDefault(sort = "createdAt", direction = DESC) }) Pageable pageable){
        Page<Release> result = flowCashProtocol.listReleases(user.getId(), pageable);
        PageImpl<ListReleasesVo> resultPageable = new PageImpl<>(result.getContent()
                .stream().map(ListReleasesVo::of)
                .collect(Collectors.toList()));

        return new PageGenerics<>(result.getTotalElements(),
                result.getTotalPages(), pageable.getPageNumber(),
                result.getSize(),result.isLast(),
                resultPageable.getContent());
    }


    @CrossOrigin
    @GetMapping("/reminder")
    @ResponseStatus(HttpStatus.OK)
    public List<ListReleasesVo> listReleasesReminder(@AuthenticationPrincipal User user){
        return flowCashProtocol
                .listReleasesReminder(user.getId())
                .stream().map(ListReleasesVo::of)
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    public ReleaseBalanceVo getBalance(@AuthenticationPrincipal User user){
        return ReleaseBalanceVo.builder()
                .balance(flowCashProtocol.getBalanceTotal(user.getId()))
                .build();
    }


}
