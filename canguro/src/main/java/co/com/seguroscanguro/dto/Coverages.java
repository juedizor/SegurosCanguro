/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author jeio
 */
public class Coverages {
    
    
    @JsonProperty (value = "VR")
    private VR VR;

    @JsonProperty (value = "CE")
    private CE CE;

    @JsonProperty (value = "PP")
    private PP PP;

    @JsonProperty (value = "AV")
    private AV AV;

    @JsonProperty (value = "CT")
    private CT CT;

    @JsonProperty (value = "AP")
    private AP AP;

    public VR getVR() {
        return VR;
    }

    public void setVR(VR VR) {
        this.VR = VR;
    }

    public CE getCE() {
        return CE;
    }

    public void setCE(CE CE) {
        this.CE = CE;
    }

    public PP getPP() {
        return PP;
    }

    public void setPP(PP PP) {
        this.PP = PP;
    }

    public AV getAV() {
        return AV;
    }

    public void setAV(AV AV) {
        this.AV = AV;
    }

    public CT getCT() {
        return CT;
    }

    public void setCT(CT CT) {
        this.CT = CT;
    }

    public AP getAP() {
        return AP;
    }

    public void setAP(AP AP) {
        this.AP = AP;
    }
    
    
    
    
    
}
