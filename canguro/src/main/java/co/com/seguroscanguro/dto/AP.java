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
public class AP {
    
    private String text;

    private String available;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getAvailable ()
    {
        return available;
    }

    public void setAvailable (String available)
    {
        this.available = available;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [text = "+text+", available = "+available+"]";
    }
    
}
