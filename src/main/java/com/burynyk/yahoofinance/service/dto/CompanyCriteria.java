package com.burynyk.yahoofinance.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.burynyk.yahoofinance.domain.Company} entity. This class is used
 * in {@link com.burynyk.yahoofinance.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter companyId;

    private StringFilter companyName;

    private StringFilter industry;

    private DoubleFilter marketCap;

    private DoubleFilter price;

    private LongFilter chartsId;

    public CompanyCriteria() {
    }

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.companyName = other.companyName == null ? null : other.companyName.copy();
        this.industry = other.industry == null ? null : other.industry.copy();
        this.marketCap = other.marketCap == null ? null : other.marketCap.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.chartsId = other.chartsId == null ? null : other.chartsId.copy();
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public StringFilter getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getIndustry() {
        return industry;
    }

    public void setIndustry(StringFilter industry) {
        this.industry = industry;
    }

    public DoubleFilter getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(DoubleFilter marketCap) {
        this.marketCap = marketCap;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public LongFilter getChartsId() {
        return chartsId;
    }

    public void setChartsId(LongFilter chartsId) {
        this.chartsId = chartsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompanyCriteria that = (CompanyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(industry, that.industry) &&
            Objects.equals(marketCap, that.marketCap) &&
            Objects.equals(price, that.price) &&
            Objects.equals(chartsId, that.chartsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        companyId,
        companyName,
        industry,
        marketCap,
        price,
        chartsId
        );
    }

    @Override
    public String toString() {
        return "CompanyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
                (companyName != null ? "companyName=" + companyName + ", " : "") +
                (industry != null ? "industry=" + industry + ", " : "") +
                (marketCap != null ? "marketCap=" + marketCap + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (chartsId != null ? "chartsId=" + chartsId + ", " : "") +
            "}";
    }

}
