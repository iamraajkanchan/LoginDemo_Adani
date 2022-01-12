package com.example.logindemo_adani

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.logindemo_adani.model.User
import com.scottyab.aescrypt.AESCrypt
import java.security.SecureRandom

class DBHelper(context : Context) : SQLiteOpenHelper(context , DB_NAME , null , DB_VERSION)
{
    private val context : Context
        get() = context
    private var IV = ByteArray(16)
    private val random = SecureRandom()

    companion object
    {
        val DB_VERSION : Int get() = 1
        val DB_NAME : String get() = "adani.db"
        val TABLE_NAME : String get() = "customer"
        val COLUMN_NAME : String get() = "name"
        val COLUMN_DOB : String get() = "dob"
        val COLUMN_ADDRESS : String get() = "address"
        val COLUMN_PIN : String get() = "pin"
        val COLUMN_USERNAME : String get() = "username"
        val COLUMN_PASSWORD : String get() = "password"
        val COLUMN_IS_LOGGED_IN : String get() = "isLoggedIn"
        val PRIVATE_KEY : String get() = "S3UC5s35WB"
    }

    override fun onCreate(db : SQLiteDatabase?)
    {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME ( $COLUMN_USERNAME TEXT PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_DOB TEXT, $COLUMN_ADDRESS TEXT, $COLUMN_PIN TEXT, $COLUMN_PASSWORD TEXT, $COLUMN_IS_LOGGED_IN INTEGER)"
                   )
    }

    override fun onUpgrade(db : SQLiteDatabase? , oldVersion : Int , newVersion : Int)
    {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun insertData(user : User) : Boolean
    {
        val db : SQLiteDatabase = writableDatabase

        /*
        random.nextBytes(IV !!)
        val encrypt = Utils.encrypt(user.password.toByteArray(Charsets.UTF_8) , Utils.generateSecretKey() , IV
                                   )
        val encryptPassword = String(encrypt , Charsets.UTF_8)
        */

        val encryptPassword = AESCrypt.encrypt(PRIVATE_KEY , user.password)
        val contentValues = ContentValues()
        with(contentValues) {
            put(COLUMN_NAME , user.name)
            put(COLUMN_DOB , user.dob)
            put(COLUMN_ADDRESS , user.address)
            put(COLUMN_PIN , user.pin)
            put(COLUMN_USERNAME , user.username)
            put(COLUMN_PASSWORD , encryptPassword)
            put(COLUMN_IS_LOGGED_IN , 1)
        }
        val result = db.insert(TABLE_NAME , null , contentValues)
        db.close()
        return if (result == - 1L)
        {
            Toast.makeText(context , "Registration is failed" , Toast.LENGTH_LONG).show()
            false
        } else
        {
            true
        }
    }

    fun checkUser(input : String) : Boolean
    {
        val db = this.readableDatabase
        var cursor : Cursor? = null
        try
        {
            cursor = db.rawQuery(
                "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ?" , arrayOf(input)
                                )
        } catch (e : Exception)
        {
            println("DBHelper :: checkUser :: Exception : $e")
        }
        val result = cursor?.count
        cursor?.close()
        return result !! > 0
    }

    @SuppressLint("Range")

    fun checkUsernameAndPassword(userInput : String , passwordInput : String) : Boolean
    {
        val db = this.readableDatabase

        /*
        random.nextBytes(IV !!)
        val encrypt = Utils.encrypt(
            passwordInput.toByteArray(Charsets.UTF_8) , Utils.generateSecretKey() , IV
                                   )
        val encryptPassword = String(encrypt , Charsets.UTF_8)
        */

        var cursor : Cursor? = null
        var storedPassword = ""
        try
        {
            cursor = db.rawQuery(
                "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ?" , arrayOf(userInput)
                                )
        } catch (e : Exception)
        {
            println("DBHelper :: checkUser :: Exception : $e")
        }
        if (cursor !!.moveToFirst())
        {
            storedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
        }
        val actualPassword = AESCrypt.decrypt(PRIVATE_KEY , storedPassword)
        return if (passwordInput.equals(actualPassword , false))
        {
            cursor.close()
            true
        } else
        {
            cursor.close()
            false
        }

    }

    @SuppressLint("Range")

    fun getUserDetail(input : String) : User
    {
        val db = this.readableDatabase
        var cursor : Cursor? = null
        var user : User? = null
        try
        {
            cursor = db.rawQuery(
                "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ?" , arrayOf(input)
                                )
        } catch (e : Exception)
        {
            println("DBHelper :: checkUser :: Exception : $e")
        }
        if (cursor?.moveToFirst() !!)
        {
            user = User(
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)) ,
                cursor.getString(cursor.getColumnIndex(COLUMN_DOB)) ,
                cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)) ,
                cursor.getString(cursor.getColumnIndex(COLUMN_PIN)) ,
                cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)) ,
                cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)) ,
                cursor.getInt(cursor.getColumnIndex(COLUMN_IS_LOGGED_IN))
                       )
        }
        cursor.close()
        return user !!
    }

    fun updateUserDetail(user : User)
    {
        val db : SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        with(contentValues) {
            put(COLUMN_NAME , user.name)
            put(COLUMN_DOB , user.dob)
            put(COLUMN_ADDRESS , user.address)
            put(COLUMN_PIN , user.pin)
        }
        db.update(TABLE_NAME , contentValues , "$COLUMN_USERNAME =?" , arrayOf(user.username))
        db.close()
    }
}