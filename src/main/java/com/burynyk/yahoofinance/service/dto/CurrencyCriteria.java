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
 * Criteria class for the {@link com.burynyk.yahoofinance.domain.Currency} entity. This class is used
 * in {@link com.burynyk.yahoofinance.web.rest.CurrencyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /currencies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CurrencyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter currencyName;

    private StringFilter symbol;

    private DoubleFilter lastPrice;

    private DoubleFilter change;

    private LongFilter chartsId;

    public CurrencyCriteria() {
    }

    public CurrencyCriteria(CurrencyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.currencyName = other.currencyName == null ? null : other.currencyName.copy();
        this.symbol = other.symbol == null ? null : other.symbol.copy();
        this.lastPrice = other.lastPrice == null ? null : other.lastPrice.copy();
        this.change = other.change == null ? null : other.change.copy();
        this.chartsId = other.chartsId == null ? null : other.chartsId.copy();
    }

    @Override
    public CurrencyCriteria copy() {
        return new CurrencyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(StringFilter currencyName) {
        this.currencyName = currencyName;
    }

    public StringFilter getSymbol() {
        return symbol;
    }

    public void setSymbol(StringFilter symbol) {
        this.symbol = symbol;
    }

    public DoubleFilter getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(DoubleFilter lastPrice) {
        this.lastPrice = lastPrice;
    }

    public DoubleFilter getChange() {
        return change;
    }

    public void setChange(DoubleFilter change) {
        this.change = change;
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
        final CurrencyCriteria that = (CurrencyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(currencyName, that.currencyName) &&
            Objects.equals(symbol, that.symbol) &&
            Objects.equals(lastPrice, that.lastPrice) &&
            Objects.equals(change, that.change) &&
            Objects.equals(chartsId, that.chartsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        currencyName,
        symbol,
        lastPrice,
        change,
        chartsId
        );
    }

    @Override
    public String toString() {
        return "CurrencyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (currencyName != null ? "currencyName=" + currencyName + ", " : "") +
                (symbol != null ? "symbol=" + symbol + ", " : "") +
                (lastPrice != null ? "lastPrice=" + lastPrice + ", " : "") +
                (change != null ? "change=" + change + ", " : "") +
                (chartsId != null ? "chartsId=" + chartsId + ", " : "") +
            "}";
    }

}
