package com.burynyk.yahoofinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "company_id", nullable = false, unique = true)
    private Long companyId;

    @NotNull
    @Size(max = 50)
    @Column(name = "company_name", length = 50, nullable = false, unique = true)
    private String companyName;

    @NotNull
    @Size(max = 50)
    @Column(name = "industry", length = 50, nullable = false)
    private String industry;

    @NotNull
    @Column(name = "market_cap", nullable = false)
    private Double marketCap;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToMany(mappedBy = "companies")
    @JsonIgnore
    private Set<Chart> charts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Company companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public Company industry(String industry) {
        this.industry = industry;
        return this;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public Company marketCap(Double marketCap) {
        this.marketCap = marketCap;
        return this;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPrice() {
        return price;
    }

    public Company price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Chart> getCharts() {
        return charts;
    }

    public Company charts(Set<Chart> charts) {
        this.charts = charts;
        return this;
    }

    public Company addCharts(Chart chart) {
        this.charts.add(chart);
        chart.getCompanies().add(this);
        return this;
    }

    public Company removeCharts(Chart chart) {
        this.charts.remove(chart);
        chart.getCompanies().remove(this);
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
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", companyName='" + getCompanyName() + "'" +
            ", industry='" + getIndustry() + "'" +
            ", marketCap=" + getMarketCap() +
            ", price=" + getPrice() +
            "}";
    }
}
