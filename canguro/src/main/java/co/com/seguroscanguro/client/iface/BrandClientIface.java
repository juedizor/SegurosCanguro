/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.client.iface;

import co.com.seguroscanguro.dto.Brand;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author jeio
 */
public interface BrandClientIface {

    public List<Brand> getBrandCar() throws IOException;

}
