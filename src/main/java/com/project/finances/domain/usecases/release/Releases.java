package com.project.finances.domain.usecases.release;

import com.project.finances.domain.protocols.ReleaseProtocol;
import com.project.finances.domain.usecases.release.dto.ReleaseDto;
import com.project.finances.domain.usecases.release.repository.ReleaseCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Releases implements ReleaseProtocol {

    private final ReleaseCommand releaseCommand;

    @Override
    public ReleaseDto createRelease(ReleaseDto dto) {
        releaseCommand.create(ReleaseDto.of(dto));

        return null;
    }
}
