package com.github.gather.dto.response.group;

import com.github.gather.entity.GroupTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailResponse {

    private Long groupId;
    private String locationName;
    private String categoryName;
    private String title;
    private String description;
    private String image;
    private Integer maxMembers;
    private LocalDate createdAt;
    private String leaderEmail;
    private Long joinedGroupMembers;
    private String leaderNickname;
    private String leaderProfilePicture;
}
