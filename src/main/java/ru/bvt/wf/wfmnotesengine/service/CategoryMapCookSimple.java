package ru.bvt.wf.wfmnotesengine.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.bvt.wf.wfmnotesengine.domain.Category;
import ru.bvt.wf.wfmnotesengine.repository.ReactiveCategoryRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CategoryMapCookSimple implements CategoryMapCook{
    private final ReactiveCategoryRepository reactiveCategoryRepository;

    public Map<String, String> cookCategoryMap(String substr) {
        Map<String, String> categoryMap = new HashMap<>();
        Mono<Category> currentCategory = null;
        String currentString;
        Long i = Long.valueOf(0);

        while (substr.indexOf('#') != -1) {
            substr = substr.substring(substr.indexOf('#') + 1, substr.length());
            var nums = new int[]{
                    substr.indexOf(' ') > 0 ? substr.indexOf(' ') : substr.length(),
                    substr.indexOf('.') > 0 ? substr.indexOf('.') : substr.length(),
                    substr.indexOf(',') > 0 ? substr.indexOf(',') : substr.length(),
                    substr.indexOf(';') > 0 ? substr.indexOf(';') : substr.length(),
                    substr.length()};
            var min = Arrays.stream(nums).min();
            currentString = substr.substring(0, min.isPresent() ? min.getAsInt() : substr.length());
            if (currentString.length()>0) {
                currentCategory = Mono.from(reactiveCategoryRepository.findByName(currentString));
                if (currentCategory == null) {
                    currentCategory = Mono.just(new Category(currentString));
                }
//                reactiveCategoryRepository.save(currentCategory);
//                categoryMap.put(currentCategory.getId(), currentCategory.getName());
                categoryMap.put(Long.toString(Math.round(Math.random()+i)), currentString);
                //TODO: Подтягивание id вместо random пытаюсь сделать в cookCategoryMapExtra, прошу помочь советом что почитать/погуглить чтобы понять
            }
            i++;
        }
        return categoryMap;
    }
/*
    public Mono<Map<Mono<String>, Mono<String>>> cookCategoryMapExtra(String substr) {

        ReactiveCategoryRepository reactiveCategoryRepository;

        Map<String, String> categoryMap = new HashMap<>();
        Mono<Map<Mono<String>, Mono<String>>> categoryMap = Mono.just(HashMap<>());
        Mono<Category> currentCategory = null;
        String currentString;

        System.out.println("cooking"+substr);

        while (substr.indexOf('#') != -1) {
            substr = substr.substring(substr.indexOf('#') + 1, substr.length());
            var nums = new int[]{
                    substr.indexOf(' ') > 0 ? substr.indexOf(' ') : substr.length(),
                    substr.indexOf('.') > 0 ? substr.indexOf('.') : substr.length(),
                    substr.indexOf(',') > 0 ? substr.indexOf(',') : substr.length(),
                    substr.indexOf(';') > 0 ? substr.indexOf(';') : substr.length(),
                    substr.length()};
            var min = Arrays.stream(nums).min();
            currentString = substr.substring(0, min.isPresent() ? min.getAsInt() : substr.length());
            if (currentString.length()>0) {
                currentCategory = Mono.from(reactiveCategoryRepository.findByName(currentString));
                if (currentCategory == null) { currentCategory = Mono.just(new Category(currentString));}
                reactiveCategoryRepository.save(currentCategory);
                categoryMap.put(currentCategory.getId(), currentCategory.getName());
            }
        }
        return categoryMap;

    }

 */
}
