package com.group.artifact.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {
    @Id
    private long id;
    @Size(min = 1)
    private String slackId;
    @OneToMany(mappedBy = "writer")
    private List<Review> review;

    public User() { }

    public User(long id, @Size(min = 1) String slackId, List<Review> review) {
        this.id = id;
        this.slackId = slackId;
        this.review = review;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSlackId() {
        return slackId;
    }

    public void setSlackId(String slackId) {
        this.slackId = slackId;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public String getWrappedSlackId() {
        return "<@" + slackId + ">";
    }
}
