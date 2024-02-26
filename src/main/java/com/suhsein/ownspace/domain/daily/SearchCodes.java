package com.suhsein.ownspace.domain.daily;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class SearchCodes {
    List<SearchCode> searchCodes = new ArrayList<>();
    public SearchCodes() {
        searchCodes.add(new SearchCode(SearchCodeName.TITLE_CONTENT, "제목+내용"));
        searchCodes.add(new SearchCode(SearchCodeName.TITLE, "제목"));
        searchCodes.add(new SearchCode(SearchCodeName.CONTENT, "내용"));
        searchCodes.add(new SearchCode(SearchCodeName.WRITER, "글쓴이"));
    }
}
