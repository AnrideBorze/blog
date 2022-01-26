package com.sarakhman.blog.entity;

import lombok.Builder;
import javax.persistence.*;

@Entity
@Table(name = "BlogDB")
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "post_seq")
    @SequenceGenerator(name="post_seq", sequenceName = "SEQ_POST",allocationSize = 50)
    long id;
    String title;
    String content;

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    boolean star;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post(long id, String title, String content, boolean star) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.star = star;
    }

    public Post() {
    }
}
