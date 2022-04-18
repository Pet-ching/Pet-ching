package com.mandarin.petching.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO Bean Validation
    private String userId;
    private String userPwd;
    private String userName;

    private LocalDate userBth;

    @Enumerated(EnumType.STRING)
    private GenderType userGender;

    private String userEmail;
    private String userTel;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    private String imageUrl;

    private String chatUrl;
}
