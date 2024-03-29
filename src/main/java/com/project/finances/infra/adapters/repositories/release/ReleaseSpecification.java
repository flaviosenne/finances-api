package com.project.finances.infra.adapters.repositories.release;

import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.Release_;
import com.project.finances.domain.entity.User_;
import org.springframework.data.jpa.domain.Specification;


public class ReleaseSpecification {
    public static Specification<Release> listReleaseByUserId(String userId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                        root.get(Release_.user).get(User_.id), userId);
    }
}
