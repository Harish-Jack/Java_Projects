package com.zoho.VConnectApp;
public class PasswordNotMatchedException extends RuntimeException
{
    PasswordNotMatchedException(String e)
    {
        super(e);
    }
}
