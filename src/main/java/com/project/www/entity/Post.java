package com.project.www.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.www.entity.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.List;

@Table(name = "Posts")
@Entity(name = "Posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name="preview", columnDefinition = "MEDIUMBLOB")
    private byte[] preview;

    @Column(name = "text", columnDefinition = "JSON")
    private String node;

    @Column(name = "published")
    private boolean published = false;

    @Column(name = "date")
    private Date date;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
