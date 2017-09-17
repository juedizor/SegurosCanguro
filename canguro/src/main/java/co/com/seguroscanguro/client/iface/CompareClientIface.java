/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.client.iface;

import co.com.seguroscanguro.dto.Compare;
import java.io.IOException;

/**
 *
 * @author jeio
 */
public interface CompareClientIface {
    
    public Compare getCompareSecures() throws IOException;
    
}
