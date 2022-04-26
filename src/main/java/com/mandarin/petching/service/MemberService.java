package com.mandarin.petching.service;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByUserEmail(member.getUserEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByUserEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getUserEmail())
                .password(member.getUserPwd())
                .roles(member.getRole().toString())
                .build();
    }

    public void updateMember(Long memberId, Member member, MultipartFile file) throws Exception{

        Member findMember = memberRepository.findById(memberId).get();
        findMember.setUserName(member.getUserName());
        findMember.setUserTel(member.getUserTel());
        findMember.setUserBth(member.getUserBth());
        findMember.setAddress(member.getAddress());

        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\memberImages";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(filePath, fileName);
        file.transferTo(saveFile);

        findMember.setImgPath("/memberImages/images/" + fileName);
    }

    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }

}