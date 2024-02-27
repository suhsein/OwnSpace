package com.suhsein.ownspace.controller.daily;

import com.suhsein.ownspace.controller.daily.dto.DailySearchDto;
import com.suhsein.ownspace.domain.daily.Daily;
import com.suhsein.ownspace.domain.daily.SearchCodeName;
import com.suhsein.ownspace.domain.daily.SearchCodes;
import com.suhsein.ownspace.service.daily.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/daily")
@RequiredArgsConstructor
public class DailySearchController {
    private final DailyService dailyService;
    private final SearchCodes searchCodes;

    @PostMapping("/search")
    public String search(@ModelAttribute("dailySearch") DailySearchDto dailySearch,
                         RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("code", dailySearch.getCode());
        redirectAttributes.addAttribute("keyword", dailySearch.getKeyword());
        return "redirect:/daily/search";
    }

    @GetMapping("/search")
    public String searchResult(@PageableDefault(size = 3, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable,
                               @ModelAttribute("dailySearch") DailySearchDto dailySearch,
                               @RequestParam("code") SearchCodeName code,
                               @RequestParam("keyword") String keyword,
                               Model model) {
        Page<Daily> dailyPages = dailyService.searchPaging(pageable, code, keyword);
        model.addAttribute("dailyPages", dailyPages);
        model.addAttribute("searchCodes", searchCodes.getSearchCodes());
        addSearchAttributes(code, keyword, model);
        return "daily/daily";
    }

    private static void addSearchAttributes(SearchCodeName code, String keyword, Model model) {
        if(code == null && keyword == null){
            model.addAttribute("onSearch", false);
        } else{
            model.addAttribute("keyword", keyword);
            model.addAttribute("code", code);
            model.addAttribute("onSearch", true);
        }
    }
}
