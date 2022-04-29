package com.project.finances.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "custom_release_shared")
@EqualsAndHashCode
public class ReleaseShared extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "user_request_share_id", referencedColumnName = "id")
    private User userRequestShare;

    @ManyToOne
    @JoinColumn(name = "user_accept_share_id", referencedColumnName = "id")
    private User userAcceptShare;

    private Boolean accepted;

    private String code;

}
