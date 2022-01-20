package spring.first.fitness.helpers;

import org.reflections.Reflections;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ElasticConstants {

    private static final Map<String, Set<Class<?>>> idxClassesMap;

    public static final String POST_INDEX = "elasticpost";


    static {
        idxClassesMap = new HashMap<>();

        new Reflections("spring.first.fitness.entity")
                .getTypesAnnotatedWith(Document.class)
                .stream()
                .map(c -> Pair.of(c.getAnnotation(Document.class).indexName(), c))
                .forEach(tuple -> {
                    if (idxClassesMap.containsKey(tuple.getFirst()))
                        idxClassesMap.get(tuple.getFirst()).add(tuple.getSecond());
                    else {
                        Set<Class<?>> set = new HashSet<>();
                        set.add(tuple.getSecond());
                        idxClassesMap.put(tuple.getFirst(), set);
                    }
                });
    }

    private ElasticConstants() {
    }

    static Set<Class<?>> getByIdxName(String idxName) {
        return idxClassesMap.get(idxName);
    }
}
