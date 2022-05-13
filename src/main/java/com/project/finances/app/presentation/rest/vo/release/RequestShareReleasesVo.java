package com.project.finances.app.presentation.rest.vo.release;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RequestShareReleasesVo {

    private String userIdAcceptShare;
}
