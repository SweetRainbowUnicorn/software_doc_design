package com.burynyk.yahoofinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Chart.
 */
@Entity
@Table(name = "chart")
public class Chart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "chart_name", length = 50, nullable = false, unique = true)
    private String chartName;

    @NotNull
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @NotNull
    @Column(name = "indicator", nullable = false)
    private Long indicator;

    @NotNull
    @Column(name = "x_axis_step", nullable = false)
    private Double xAxisStep;

    @NotNull
    @Column(name = "y_axis_step", nullable = false)
    private Double yAxisStep;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @ManyToMany
    @JoinTable(name = "chart_companies",
               joinColumns = @JoinColumn(name = "chart_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "companies_id", referencedColumnName = "id"))
    private Set<Company> companies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "chart_currencies",
               joinColumns = @JoinColumn(name = "chart_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "currencies_id", referencedColumnName = "id"))
    private Set<Currency> currencies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "chart_indexes",
               joinColumns = @JoinColumn(name = "chart_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "indexes_id", referencedColumnName = "id"))
    private Set<WorldIndex> indexes = new HashSet<>();

    @OneToOne(mappedBy = "chart")
    @JsonIgnore
    private SavedChart savedChart;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChartName() {
        return chartName;
    }

    public Chart chartName(String chartName) {
        this.chartName = chartName;
        return this;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public Long getItemId() {
        return itemId;
    }

    public Chart itemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getIndicator() {
        return indicator;
    }

    public Chart indicator(Long indicator) {
        this.indicator = indicator;
        return this;
    }

    public void setIndicator(Long indicator) {
        this.indicator = indicator;
    }

    public Double getxAxisStep() {
        return xAxisStep;
    }

    public Chart xAxisStep(Double xAxisStep) {
        this.xAxisStep = xAxisStep;
        return this;
    }

    public void setxAxisStep(Double xAxisStep) {
        this.xAxisStep = xAxisStep;
    }

    public Double getyAxisStep() {
        return yAxisStep;
    }

    public Chart yAxisStep(Double yAxisStep) {
        this.yAxisStep = yAxisStep;
        return this;
    }

    public void setyAxisStep(Double yAxisStep) {
        this.yAxisStep = yAxisStep;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Chart startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Chart endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Chart companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public Chart addCompanies(Company company) {
        this.companies.add(company);
        company.getCharts().add(this);
        return this;
    }

    public Chart removeCompanies(Company company) {
        this.companies.remove(company);
        company.getCharts().remove(this);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Set<Currency> getCurrencies() {
        return currencies;
    }

    public Chart currencies(Set<Currency> currencies) {
        this.currencies = currencies;
        return this;
    }

    public Chart addCurrencies(Currency currency) {
        this.currencies.add(currency);
        currency.getCharts().add(this);
        return this;
    }

    public Chart removeCurrencies(Currency currency) {
        this.currencies.remove(currency);
        currency.getCharts().remove(this);
        return this;
    }

    public void setCurrencies(Set<Currency> currencies) {
        this.currencies = currencies;
    }

    public Set<WorldIndex> getIndexes() {
        return indexes;
    }

    public Chart indexes(Set<WorldIndex> worldIndices) {
        this.indexes = worldIndices;
        return this;
    }

    public Chart addIndexes(WorldIndex worldIndex) {
        this.indexes.add(worldIndex);
        worldIndex.getCharts().add(this);
        return this;
    }

    public Chart removeIndexes(WorldIndex worldIndex) {
        this.indexes.remove(worldIndex);
        worldIndex.getCharts().remove(this);
        return this;
    }

    public void setIndexes(Set<WorldIndex> worldIndices) {
        this.indexes = worldIndices;
    }

    public SavedChart getSavedChart() {
        return savedChart;
    }

    public Chart savedChart(SavedChart savedChart) {
        this.savedChart = savedChart;
        return this;
    }

    public void setSavedChart(SavedChart savedChart) {
        this.savedChart = savedChart;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chart)) {
            return false;
        }
        return id != null && id.equals(((Chart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chart{" +
            "id=" + getId() +
            ", chartName='" + getChartName() + "'" +
            ", itemId=" + getItemId() +
            ", indicator=" + getIndicator() +
            ", xAxisStep=" + getxAxisStep() +
            ", yAxisStep=" + getyAxisStep() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
