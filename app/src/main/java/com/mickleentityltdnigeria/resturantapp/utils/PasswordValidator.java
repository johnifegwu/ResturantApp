package com.mickleentityltdnigeria.resturantapp.utils;

import java.util.*;

//Author: John Ifegwu
public class PasswordValidator
{

    public static boolean ValidatePassword(String pw, int minL, int maxL)
    {
        boolean IsFullLength = false;
        boolean HasSpcChar = false;
        boolean HasNum = false;
        boolean NoWhiteSp = false;

        //Check length
        if (pw.length() >= minL && pw.length() <= maxL)
        {
            IsFullLength = true;
        }

        //Check number
        for (int x = 0; x < 10; x++)
        {
            if (pw.contains(String.valueOf(x)))
            {
                HasNum = true;
                break;
            }
        }

       /* //Check special characters
        char[] spChars = {'!', '@', '#', '$', '%', '^', '&', '*', '~', '<', '>', '?', '+', '×', '÷', '=', '/', '_', '€', '£', '¥', '₩', '\\', '|'};
        for (char x : spChars)
        {
            if (pw.contains(String.valueOf(x)))
            {
                HasSpcChar = true;
                break;
            }
        }*/

        HasSpcChar = true;

        //Check white spaces
        if (!pw.contains(" "))
        {
            NoWhiteSp = true;
        }

        return (HasSpcChar && HasNum && IsFullLength && NoWhiteSp);
    }


}
