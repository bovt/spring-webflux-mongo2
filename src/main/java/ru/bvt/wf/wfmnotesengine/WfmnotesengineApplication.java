package ru.bvt.wf.wfmnotesengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.bvt.wf.wfmnotesengine.domain.Category;
import ru.bvt.wf.wfmnotesengine.repository.ReactiveCategoryRepository;

@EnableMongoRepositories
@SpringBootApplication
public class WfmnotesengineApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(WfmnotesengineApplication.class, args);
        Category category = new Category("Cat");
        ReactiveCategoryRepository categoryReactiveRepository = context.getBean(ReactiveCategoryRepository.class);
        categoryReactiveRepository.save(category).subscribe(c -> System.out.println(c.getName()));;

    }

}
