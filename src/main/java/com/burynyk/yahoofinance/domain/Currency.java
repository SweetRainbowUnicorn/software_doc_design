package com.burynyk.yahoofinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "currency_name", length = 50, nullable = false, unique = true)
    private String currencyName;

    @NotNull
    @Size(max = 50)
    @Column(name = "symbol", length = 50, nullable = false, unique = true)
    private String symbol;

    @NotNull
    @Column(name = "last_price", nullable = false)
    private Double lastPrice;

    @NotNull
    @Column(name = "jhi_change", nullable = false)
    private Double change;

    @ManyToMany(mappedBy = "currencies")
    @JsonIgnore
    private Set<Chart> charts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public Currency currencyName(String currencyName) {
        this.currencyName = currencyName;
        return this;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public Currency symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public Currency lastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
        return this;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getChange() {
        return change;
    }

    public Currency change(Double change) {
        this.change = change;
        return this;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Set<Chart> getCharts() {
        return charts;
    }

    public Currency charts(Set<Chart> charts) {
        this.charts = charts;
        return this;
    }

    public Currency addCharts(Chart chart) {
        this.charts.add(chart);
        chart.getCurrencies().add(this);
        return this;
    }

    public Currency removeCharts(Chart chart) {
        this.charts.remove(chart);
        chart.getCurrencies().remove(this);
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
        if (!(o instanceof Currency)) {
            return false;
        }
        return id != null && id.equals(((Currency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", currencyName='" + getCurrencyName() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", lastPrice=" + getLastPrice() +
            ", change=" + getChange() +
            "}";
    }
}
