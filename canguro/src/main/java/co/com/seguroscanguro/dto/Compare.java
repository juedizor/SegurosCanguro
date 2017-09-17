/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.dto;

/**
 *
 * @author jeio
 */
public class Compare {

    private Quotes[] quotes;

    private String leadId;

    private String comparisonId;

    public Quotes[] getQuotes() {
        return quotes;
    }

    public void setQuotes(Quotes[] quotes) {
        this.quotes = quotes;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getComparisonId() {
        return comparisonId;
    }

    public void setComparisonId(String comparisonId) {
        this.comparisonId = comparisonId;
    }

    @Override
    public String toString() {
        return "ClassPojo [quotes = " + quotes + ", leadId = " + leadId + ", comparisonId = " + comparisonId + "]";
    }

}
