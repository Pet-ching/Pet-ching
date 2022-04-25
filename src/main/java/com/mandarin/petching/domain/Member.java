package com.mandarin.petching.domain;

import com.mandarin.petching.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String userId;

    private String userName;

    @OneToMany(mappedBy = "member")
    private List<Pet> petList = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private PetSitter petSitter;

    private String userBth;

    @Enumerated(EnumType.STRING)
    private GenderType userGender;

    @Column(unique = true)
    private String userEmail;

    private String userPwd;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Board> board = new ArrayList<>();

    private String userTel;

    private String imgUrl;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
//        member.setUserId(memberFormDto.getUserId());
        member.setUserName(memberFormDto.getUserName());
        member.setUserEmail(memberFormDto.getUserEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setUserBth(memberFormDto.getUserBth());
        member.setUserTel(memberFormDto.getUserTel());
        member.setUserGender(memberFormDto.getUserGender());
        String password = passwordEncoder.encode(memberFormDto.getUserPwd());
        member.setUserPwd(password);
        member.setRole(Role.USER);

        return member;
    }
}
