// code by. hyeok
package com.mandarin.petching.controller;

import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {

    @Autowired
    private InfoService infoService;

    @GetMapping("/matching/list")
    public String list(Model model, @PageableDefault(page = 0, size = 2, sort = "workingArea", direction = Sort.Direction.ASC) Pageable pageable, String searchKeyword) {

        Page<PetSitter> list = null;

        if (searchKeyword == null) {
            list = infoService.sitterList(pageable);
        } else {
            list = infoService.sitterSearchList(searchKeyword, pageable);
        }
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "infolist";
    }

    @GetMapping("/matching/details")
    public String search(Model model, Long id) {
        model.addAttribute("petSitter", infoService.findById(id));

        return "details";
    }

    @GetMapping(value = {"/map"})
    public String map() {
        return "map";
    }


//    @GetMapping("/maching/list/{area}")
//    public String search(Model model, String area) {
//        model.addAttribute("area", infoService.area);
//        return "infolist";
//    }

}
