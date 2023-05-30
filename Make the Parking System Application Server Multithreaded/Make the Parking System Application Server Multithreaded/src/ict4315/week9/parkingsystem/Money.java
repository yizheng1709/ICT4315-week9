/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict4315.parkingsystem;

import java.io.Serializable;
import java.util.Currency;

/**
 *
 * 
 */

public final class Money implements Serializable {
    
    private final long amount;
    private final Currency currency;
    
    public Money(long amount){
        this.amount = amount;
        this.currency = Currency.getInstance("USD");
    }

    public long getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
    
    @Override
    public String toString(){
        return String.valueOf(amount) + " " + currency;
    }

    @Override
    public boolean equals(Object obj) {
        final Money other = (Money) obj;
        if (this.amount != other.getAmount()) {
            return false;
        }
        if (!this.currency.equals(other.getCurrency())) {
            return false;
        }
        return true;
    }
    
}

