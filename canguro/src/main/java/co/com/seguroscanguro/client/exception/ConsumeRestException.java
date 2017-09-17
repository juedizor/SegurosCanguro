/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.client.exception;

/**
 *
 * @author jeio
 */
public class ConsumeRestException extends Exception {

    public ConsumeRestException() {
    }

    public ConsumeRestException(String mensaje) {
        super(mensaje);
    }

}
