package com.suhsein.ownspace.domain.daily;

import lombok.Data;

@Data
public class DailySearchDto {
    private SearchCodeName code;
    private String keyword;
}
