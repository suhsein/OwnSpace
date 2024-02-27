package com.suhsein.ownspace.controller.daily.dto;

import com.suhsein.ownspace.domain.daily.SearchCodeName;
import lombok.Data;

@Data
public class DailySearchDto {
    private SearchCodeName code;
    private String keyword;
}
