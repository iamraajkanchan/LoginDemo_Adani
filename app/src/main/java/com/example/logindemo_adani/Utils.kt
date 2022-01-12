package com.example.logindemo_adani

import android.content.Context
import android.content.Intent
import android.widget.EditText
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Utils
{
    fun validateName(input : EditText) : Boolean
    {
        return if (! input.text.isNullOrBlank())
        {
            input.error = null
            true
        } else
        {
            input.error = "Invalid Name!!!"
            false
        }
    }

    fun validateDOB(input : EditText) : Boolean
    {
        return if (! input.text.isNullOrBlank())
        {
            input.error = null
            true
        } else
        {
            input.error = "Invalid Date of Birth!!!"
            false
        }
    }

    fun validateAddress(input : EditText) : Boolean
    {
        return if (! input.text.isNullOrBlank())
        {
            input.error = null
            true
        } else
        {
            input.error = "Invalid Address!!!"
            false
        }
    }

    fun validateUsername(input : EditText) : Boolean
    {
        return if (! input.text.isNullOrBlank())
        {
            input.error = null
            true
        } else
        {
            input.error = "Invalid Username!!!"
            false
        }
    }

    fun validatePassword(input : EditText) : Boolean
    {
        val PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
        when
        {
            input.text.isNullOrBlank() ->
            {
                input.error = "Invalid Password!!!"
                return false
            }
            ! PASSWORD_PATTERN.matcher(input.text).matches() ->
            {
                input.error = "Please enter valid password!!!"
                return false
            }
            else ->
            {
                input.error = null
                return true
            }
        }
    }

    fun validatePin(input : String) : Boolean
    {
        return ! input.isNullOrBlank() && ! input.equals("" , false)
    }

    fun encrypt(text : ByteArray , key : SecretKey , IV : ByteArray) : ByteArray
    {
        val cipher = Cipher.getInstance("AES")
        val keySpec = SecretKeySpec(key.encoded , "AES")
        val ivSpec = IvParameterSpec(IV)
        cipher.init(Cipher.ENCRYPT_MODE , keySpec , ivSpec)
        return cipher.doFinal(text)
    }

    fun decrypt(cipherText : ByteArray , key : SecretKey , IV : ByteArray) : String
    {
        try
        {
            val cipher = Cipher.getInstance("AES")
            val keySpec = SecretKeySpec(key.encoded , "AES")
            val ivSpec = IvParameterSpec(IV)
            cipher.init(Cipher.DECRYPT_MODE , keySpec , ivSpec)
            return cipher.doFinal(cipherText).toString()
        } catch (e : Exception)
        {
            println("Utils :: decrypt :: Exception : $e")
        }
        return ""
    }

    fun generateSecretKey() : SecretKey
    {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }

    fun goBackToHome(context : Context) : Intent
    {
        return Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}