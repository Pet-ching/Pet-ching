package com.mandarin.petching.controller;

import com.mandarin.petching.dto.MemberFormDto;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mandarin.petching.domain.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


    @GetMapping(value = "/join")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/join")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "/index";
    }

    @GetMapping(value = "/login")
    public String loginMember() {
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    @GetMapping("/success")
    public ModelAndView loginSuccess(Authentication auth) {
        ModelAndView mav = new ModelAndView("/member/success");
        System.out.println("Authentication 안에 해당 로그인 사용자 정보 담겨 있음");
        System.out.println("유저 로그인 성공");
        return mav;
    }

    @GetMapping("/logout")
    public String logout() {
        System.out.println("유저 로그아웃 성공");
        return "/member/logout";
    }

    @GetMapping({"", "/"})
    public String choice() {
        return "/index";
    }

    @GetMapping("/withdrawal")
    public String withDrawal(Model model, Authentication authentication) {
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);
        model.addAttribute("member", member);
        return "withdrawal";
    }


    @PostMapping(value = "/withdrawaldo")
    public String deleteMember(Long id, Authentication authentication, Model model) {
//        memberService.deleteMember(id);
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        try {
            memberService.deleteMember(member.getId());
            SecurityContextHolder.clearContext(); // 스프링 시큐리티 탈퇴 시 로그아웃 처리
        } catch (Exception e) {
            System.out.println("해당 계정의 게시물을 삭제하세요");
            e.printStackTrace();
        }
        return "redirect:/";
//        return "/index";
    }

}