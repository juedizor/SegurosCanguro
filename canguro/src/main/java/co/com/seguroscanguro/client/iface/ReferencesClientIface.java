/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.client.iface;

import co.com.seguroscanguro.client.exception.ConsumeRestException;
import co.com.seguroscanguro.dto.Reference;
import java.util.List;

/**
 *
 * @author jeio
 */
public interface ReferencesClientIface {

    public List<Reference> getReferenceCar(String brand, String model) throws ConsumeRestException;

}
