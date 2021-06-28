package spring.first.fitness.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.first.fitness.entity.Tags;
import spring.first.fitness.repos.TagRepository;
import spring.first.fitness.services.TagService;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    @Override
    public void addOrSaveTag(Tags tag) {
        tagRepository.save(tag);
    }

    @Override
    public List<Tags> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tags getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
