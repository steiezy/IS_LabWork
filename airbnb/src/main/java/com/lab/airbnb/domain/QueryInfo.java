package com.lab.airbnb.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class QueryInfo {
    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int size = 50;
}
