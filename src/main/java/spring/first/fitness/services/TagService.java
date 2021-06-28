package spring.first.fitness.services;



import spring.first.fitness.entity.Tags;

import java.util.List;

public interface TagService {

    void addOrSaveTag(Tags tag);
    List<Tags> getAllTags();
    Tags getTag(Long id);
    void deleteTag(Long id);
}
