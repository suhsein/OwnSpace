package com.suhsein.ownspace.domain.daily;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCode {
    private SearchCodeName code;
    private String displayName;
}
