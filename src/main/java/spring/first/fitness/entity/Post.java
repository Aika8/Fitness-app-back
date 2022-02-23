package spring.first.fitness.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.first.fitness.util.DateUtil;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String cover;

    private Integer access;

    private Integer priority;

    @Column(name = "creation_date")
    @JsonFormat(pattern = DateUtil.DEFAULT_DATE_PATTERN)
    private LocalDateTime dateOfCreation;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Users> users;

}
