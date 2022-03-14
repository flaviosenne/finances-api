package com.project.finances.domain.usecases.release.repository;

import com.project.finances.domain.entity.Release;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class ReleaseSpecificationTest {

    @Test
    void test(){
        String userId = "user-id-valid";

        Specification<Release> result = ReleaseSpecification.listReleaseByUserId(userId);

        BDDAssertions.assertThat(result).isNotNull();
    }

}