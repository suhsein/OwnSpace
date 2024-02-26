package com.example.demo.domain.daily;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class SearchCodes {
    List<SearchCode> searchCodes = new ArrayList<>();
    public SearchCodes() {
        searchCodes.add(new SearchCode("TITLE_CONTENT", "제목+내용"));
        searchCodes.add(new SearchCode("TITLE", "제목"));
        searchCodes.add(new SearchCode("CONTENT", "내용"));
        searchCodes.add(new SearchCode("WRITER", "글쓴이"));
    }
}
