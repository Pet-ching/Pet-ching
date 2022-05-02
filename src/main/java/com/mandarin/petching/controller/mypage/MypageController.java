package com.mandarin.petching.controller.mypage;

import com.mandarin.petching.domain.ChatRoom;
import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.MemberService;
import com.mandarin.petching.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MyPageService myPageService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping
    public String mypage(Authentication authentication, @RequestParam String type, Model model) {

        Member member = getMember(authentication);

        model.addAttribute("member", member);
        model.addAttribute("type", type);
        return "mypage/mypage";
    }

    @GetMapping("/chats")
    public String viewChatList(Authentication authentication, Model model) {

        Member member = getMember(authentication);

        List<ChatRoom> chatRoomList = myPageService.getChatList(member.getId());

        model.addAttribute("chatRoomList", chatRoomList);

        return "mypage/chatList";
    }

    @GetMapping("/member/edit")
    public String editMemberForm(Authentication authentication, Model model) {

        Member member = getMember(authentication);

        model.addAttribute("member", member);

        return "mypage/memberEdit";
    }

    @PostMapping("/member/edit")
    public String editMember(@Validated Member member,
                             BindingResult bindingResult,
                             MultipartFile file,
                             Authentication authentication,
                             Model model) throws Exception{

        if(bindingResult.hasErrors()) {
            return "mypage/memberEdit";
        }

        Member findMember = getMember(authentication);

        memberService.updateMember(findMember.getId(), member, file);

        return "redirect:/mypage?type=petowner";
    }

    private Member getMember(Authentication authentication) {
        String userName = authentication.getName();
        return memberRepository.findByUserEmail(userName);
    }
}
