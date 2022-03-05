package com.project.finances.domain.protocols;

import com.project.finances.domain.entity.Release;

public interface ReleaseProtocol {

    Release createRelease(Release release);
}
