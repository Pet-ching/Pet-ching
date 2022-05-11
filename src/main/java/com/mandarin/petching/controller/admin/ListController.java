package com.mandarin.petching.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ListController {

    @GetMapping("/members")
    public String memberList() {
        return "admin/members";
    }

    @GetMapping("/reservations")
    public String reservationList() {
        return "admin/reservations";
    }
}
