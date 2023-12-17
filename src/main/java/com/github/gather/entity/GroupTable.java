package com.github.gather.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "group_table")
public class GroupTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_idx")
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location locationId;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "max_members")
    private Integer maxMembers;

    @Column(name = "created_at")
    //@Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    // 생성자, 게터, 세터 등 필요한 메서드 추가

    public GroupTable(Category categoryId, Location locationId, String title, String image, String description, Integer maxMembers, LocalDate createdAt, Boolean isDeleted) {
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.title = title;
        this.image = image;
        this.description = description;
        this.maxMembers = maxMembers;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    public void updateGroupInfo(Category categoryId, Location locationId, String title,String description,String image,Integer maxMembers, LocalDate createdAt) {
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.maxMembers = maxMembers;
        this.createdAt = createdAt;
    }
}

