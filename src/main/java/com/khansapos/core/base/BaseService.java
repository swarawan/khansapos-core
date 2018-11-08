package com.khansapos.core.base;

import org.springframework.http.HttpHeaders;

public interface BaseService {

    String validateHeader(HttpHeaders headers);
}
