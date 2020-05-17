package com.burynyk.yahoofinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A WorldIndex.
 */
@Entity
@Table(name = "world_index")
public class WorldIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "index_name", length = 50, nullable = false, unique = true)
    private String indexName;

    @NotNull
    @Column(name = "last_price", nullable = false)
    private Double lastPrice;

    @NotNull
    @Column(name = "jhi_change", nullable = false)
    private Double change;

    @NotNull
    @Column(name = "volume", nullable = false)
    private Long volume;

    @ManyToMany(mappedBy = "indexes")
    @JsonIgnore
    private Set<Chart> charts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndexName() {
        return indexName;
    }

    public WorldIndex indexName(String indexName) {
        this.indexName = indexName;
        return this;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public WorldIndex lastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
        return this;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getChange() {
        return change;
    }

    public WorldIndex change(Double change) {
        this.change = change;
        return this;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Long getVolume() {
        return volume;
    }

    public WorldIndex volume(Long volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Set<Chart> getCharts() {
        return charts;
    }

    public WorldIndex charts(Set<Chart> charts) {
        this.charts = charts;
        return this;
    }

    public WorldIndex addCharts(Chart chart) {
        this.charts.add(chart);
        chart.getIndexes().add(this);
        return this;
    }

    public WorldIndex removeCharts(Chart chart) {
        this.charts.remove(chart);
        chart.getIndexes().remove(this);
        return this;
    }

    public void setCharts(Set<Chart> charts) {
        this.charts = charts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorldIndex)) {
            return false;
        }
        return id != null && id.equals(((WorldIndex) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WorldIndex{" +
            "id=" + getId() +
            ", indexName='" + getIndexName() + "'" +
            ", lastPrice=" + getLastPrice() +
            ", change=" + getChange() +
            ", volume=" + getVolume() +
            "}";
    }
}
