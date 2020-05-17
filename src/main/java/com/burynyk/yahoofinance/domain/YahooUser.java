package com.burynyk.yahoofinance.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A YahooUser.
 */
@Entity
@Table(name = "yahoo_user")
public class YahooUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(name = "age", nullable = false)
    private Long age;

    @Size(max = 50)
    @Column(name = "interest", length = 50)
    private String interest;

    @OneToMany(mappedBy = "yahooUser")
    private Set<SavedChart> savedCharts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public YahooUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getAge() {
        return age;
    }

    public YahooUser age(Long age) {
        this.age = age;
        return this;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getInterest() {
        return interest;
    }

    public YahooUser interest(String interest) {
        this.interest = interest;
        return this;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public Set<SavedChart> getSavedCharts() {
        return savedCharts;
    }

    public YahooUser savedCharts(Set<SavedChart> savedCharts) {
        this.savedCharts = savedCharts;
        return this;
    }

    public YahooUser addSavedCharts(SavedChart savedChart) {
        this.savedCharts.add(savedChart);
        savedChart.setYahooUser(this);
        return this;
    }

    public YahooUser removeSavedCharts(SavedChart savedChart) {
        this.savedCharts.remove(savedChart);
        savedChart.setYahooUser(null);
        return this;
    }

    public void setSavedCharts(Set<SavedChart> savedCharts) {
        this.savedCharts = savedCharts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof YahooUser)) {
            return false;
        }
        return id != null && id.equals(((YahooUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "YahooUser{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", age=" + getAge() +
            ", interest='" + getInterest() + "'" +
            "}";
    }
}
