package com.mandarin.petching.domain;

import com.mandarin.petching.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@DiscriminatorColumn
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String userName;

    @OneToMany(mappedBy = "member")
    private List<Pet> petList = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private PetSitter petSitter;

    private LocalDate userBth;

    @Enumerated(EnumType.STRING)
    private GenderType userGender;

    @Column(unique = true)
    private String userEmail;

    private String userPwd;

    private String address;

<<<<<<< HEAD
    private String chatUrl;

    @OneToMany(mappedBy = "member")
    private List<Board> board = new ArrayList<Board>();
=======
    @Enumerated(EnumType.STRING)
    private Role role;

    
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setUserId(memberFormDto.getUserId());
        member.setUserName(memberFormDto.getUserName());
        member.setUserEmail(memberFormDto.getUserEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getUserPwd());
        member.setUserPwd(password);
        member.setRole(Role.admin);
        return member;
    }
>>>>>>> 12ac8b9c295e6290d159d34cb15514847d26ae59
}
