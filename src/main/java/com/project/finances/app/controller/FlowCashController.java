package com.project.finances.app.controller;

//import br.com.monkey.ecx.annotation.SearchParameter;
//import br.com.monkey.ecx.annotation.SearchParameter;
import com.project.finances.app.utils.PageGenerics;
import com.project.finances.app.vo.release.ListReleasesVo;
import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.protocols.FlowCashProtocol;
import com.project.finances.domain.protocols.ReleaseReminderProtocol;
import com.project.finances.domain.usecases.release.dto.ReleaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private final ReleaseReminderProtocol releaseReminderProtocol;

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
//                                                     @SearchParameter Specification<Release> search,
                                                     @SortDefault.SortDefaults({ @SortDefault(sort = "createdAt", direction = DESC) }) Pageable pageable){
        Page<Release> result = flowCashProtocol.listReleases(user.getId(), null, pageable);
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
        return releaseReminderProtocol
                .listReleasesReminder(user.getId())
                .stream().map(ListReleasesVo::of)
                .collect(Collectors.toList());
    }
    @CrossOrigin
    @PutMapping("/share")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestShareCashFlow(){

    }
}
