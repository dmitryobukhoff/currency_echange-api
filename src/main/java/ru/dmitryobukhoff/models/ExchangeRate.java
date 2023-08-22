package ru.dmitryobukhoff.models;

import java.math.BigDecimal;

public class ExchangeRate {
    private Long id;
    private Currency base;
    private Currency target;
    private BigDecimal rate;

    public ExchangeRate() {
    }

    public ExchangeRate(Currency base, Currency target, BigDecimal rate) {
        this.base = base;
        this.target = target;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getBase() {
        return base;
    }

    public void setBase(Currency base) {
        this.base = base;
    }

    public Currency getTarget() {
        return target;
    }

    public void setTarget(Currency target) {
        this.target = target;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
