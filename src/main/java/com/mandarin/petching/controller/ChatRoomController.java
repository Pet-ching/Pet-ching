package com.mandarin.petching.controller;

import com.mandarin.petching.domain.ChatMessage;
import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;
    private final MemberRepository memberRepository;

    @GetMapping("/chat/{petSitterId}")
    public String createRoom(Authentication authentication, @PathVariable Long petSitterId) {

        Member member = getMember(authentication);
        Long petOwnerId = member.getId();

        Long roomId = chatService.createRoom(petOwnerId, petSitterId);
        return "redirect:/chat/room/" + roomId;
    }

    @GetMapping("/chat/room/{roomId}")
    public String enterRoom(Authentication authentication, @PathVariable Long roomId, Model model) {

        List<ChatMessage> chatList = chatService.findAllChatByRoomId(roomId);

        Member member = getMember(authentication);
        String username = member.getUserName();

        model.addAttribute("roomId", roomId);
        model.addAttribute("chatList", chatList);
        model.addAttribute("username", username);

        return "chat/room";
    }

    private Member getMember(Authentication authentication) {
        String userName = authentication.getName();
        return memberRepository.findByUserEmail(userName);
    }
}
