package ru.bvt.wf.wfmnotesengine.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface CategoryMapCook {
    Map<String, String> cookCategoryMap(String substr);
//    Mono<Map<Mono<String>, Mono<String>>> cookCategoryMapExtra(String substr) {

 }
