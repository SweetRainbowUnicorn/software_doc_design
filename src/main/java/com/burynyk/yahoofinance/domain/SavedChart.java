package com.burynyk.yahoofinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A SavedChart.
 */
@Entity
@Table(name = "saved_chart")
public class SavedChart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "chart_id", nullable = false)
    private Long chartId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

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

    @OneToOne
    @JoinColumn(unique = true)
    private Chart chart;

    @ManyToOne
    @JsonIgnoreProperties("savedCharts")
    private YahooUser yahooUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChartId() {
        return chartId;
    }

    public SavedChart chartId(Long chartId) {
        this.chartId = chartId;
        return this;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public Long getUserId() {
        return userId;
    }

    public SavedChart userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public SavedChart itemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getIndicator() {
        return indicator;
    }

    public SavedChart indicator(Long indicator) {
        this.indicator = indicator;
        return this;
    }

    public void setIndicator(Long indicator) {
        this.indicator = indicator;
    }

    public Double getxAxisStep() {
        return xAxisStep;
    }

    public SavedChart xAxisStep(Double xAxisStep) {
        this.xAxisStep = xAxisStep;
        return this;
    }

    public void setxAxisStep(Double xAxisStep) {
        this.xAxisStep = xAxisStep;
    }

    public Double getyAxisStep() {
        return yAxisStep;
    }

    public SavedChart yAxisStep(Double yAxisStep) {
        this.yAxisStep = yAxisStep;
        return this;
    }

    public void setyAxisStep(Double yAxisStep) {
        this.yAxisStep = yAxisStep;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public SavedChart startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public SavedChart endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Chart getChart() {
        return chart;
    }

    public SavedChart chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public YahooUser getYahooUser() {
        return yahooUser;
    }

    public SavedChart yahooUser(YahooUser yahooUser) {
        this.yahooUser = yahooUser;
        return this;
    }

    public void setYahooUser(YahooUser yahooUser) {
        this.yahooUser = yahooUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SavedChart)) {
            return false;
        }
        return id != null && id.equals(((SavedChart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SavedChart{" +
            "id=" + getId() +
            ", chartId=" + getChartId() +
            ", userId=" + getUserId() +
            ", itemId=" + getItemId() +
            ", indicator=" + getIndicator() +
            ", xAxisStep=" + getxAxisStep() +
            ", yAxisStep=" + getyAxisStep() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
