package com.github.gather.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="token_blacklist")
public class TokenBlacklist {

    @Id
    @Column(name="refresh_token")
    private String refreshToken;

    @Column(name="expiration_date", nullable = false)
    private Date expirationDate;
}
