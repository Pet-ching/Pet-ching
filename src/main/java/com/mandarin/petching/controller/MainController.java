package com.mandarin.petching.controller;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MemberRepository memberRepository;

    @GetMapping(value = "/")
    public String main(Authentication authentication, Model model){
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);
        model.addAttribute("member", member);

        return "index";
    }
}