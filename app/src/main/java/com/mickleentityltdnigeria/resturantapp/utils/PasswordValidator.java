package com.mickleentityltdnigeria.resturantapp.utils;

import java.util.*;

//Author: John Ifegwu
public class PasswordValidator
{

   /* public static void main(String[] args)
    {
        System.out.println("Enter password: \n Minimum length is 5 \n Maximum length is 10 \n Must contain at least one special character and one number.");
        String pw = "";
        pw = new Scanner(System.in).nextLine();
        if (ValidatePassword(pw, 5, 10))
        {
            System.out.println("Congratulations, your password is valid.");
        }
        else
        {
            System.out.println("Your password is not valid.");
        }
    }
*/
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

        //Check special characters
        char[] spChars = {'!', '@', '#', '$', '%', '^', '&', '*', '~', '<', '>', '?', '+', '×', '÷', '=', '/', '_', '€', '£', '¥', '₩', '\\', '|'};
        for (char x : spChars)
        {
            if (pw.contains(String.valueOf(x)))
            {
                HasSpcChar = true;
                break;
            }
        }

        //Check white spaces
        if (!pw.contains(" "))
        {
            NoWhiteSp = true;
        }

        return (HasSpcChar && HasNum && IsFullLength && NoWhiteSp);
    }


}
