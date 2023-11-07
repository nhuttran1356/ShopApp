package com.nhuttran.shopapp_ecom.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "social_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider", length = 20, nullable = false)
    private String provider;
    @Column(name = "provider_id", length = 50)
    private String providerId;

    @Column(name = "name", length = 150)
    private String name;
    @Column(name = "email", length = 150)
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
