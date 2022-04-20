package com.mandarin.petching.controller;

import com.mandarin.petching.domain.ChatMessage;
import com.mandarin.petching.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/chat/{buyerId}/{sellerId}")
    public String createRoom(@PathVariable Long buyerId, @PathVariable Long sellerId) {
        Long roomId = chatService.createRoom(buyerId, sellerId);
        log.info("roomId = {}", roomId);
        return "redirect:/chat/room/" + roomId;
    }

    /**
     * 로그인 기능 구현 후 수정 필요
     * @RequestParam으로 사용자 이름 받아옴 -> 현재 로그인한 사용자 정보 받아오기
     */
    @GetMapping("/chat/room/{roomId}")
    public String enterRoom(@PathVariable Long roomId, @RequestParam(required = false) String username, Model model, HttpServletRequest request) {

        List<ChatMessage> chatList = chatService.findAllChatByRoomId(roomId);

        model.addAttribute("roomId", roomId);
        model.addAttribute("chatList", chatList);

        // TODO 로그인 기능 구현 후 수정
        model.addAttribute("username", username);

        if (username != null) {
            HttpSession session = request.getSession();

            if (session.getAttribute(username) == null) {
                session.setAttribute(username, roomId);
            } else {
                return "chatTemp";
            }
        }

        return "chat/room";
    }
}
