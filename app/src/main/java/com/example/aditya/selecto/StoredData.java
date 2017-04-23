package com.example.aditya.selecto;

/**
 * Created by aditya on 30/3/17.
 */

public class StoredData {
    String _key;
    String _value;


    // constructor
    public StoredData(String key, String value)
    {
        this._key = key;
        this._value = value;
    }



    public String getkey(){
        return this._key;
    }

    public void setkey(String key)
    {
        this._key = key;
    }


    public String getvalue()
    {
        return this._value;
    }


    public void setvalue(String value)
    {
        this._value= value;
    }

}
