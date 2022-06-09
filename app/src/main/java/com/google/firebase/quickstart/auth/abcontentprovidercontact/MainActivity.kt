package com.google.firebase.quickstart.auth.abcontentprovidercontact
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity()
{
    //this variable stores the contact details
    //in the form of an array
    var contact_fields = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID

    ).toTypedArray()

    //using listview to show your contacts in list form
    lateinit var list_view:ListView;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Binding ListView to a variable
        //to display the contact list

        list_view = findViewById(R.id.contact_list_view);

        //Checking if the user has granted
        //permission to access his contacts or not

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) !=
            PackageManager.PERMISSION_GRANTED)
        {
            //If permission not granted
            //then seek permission from user

            ActivityCompat.requestPermissions(this,
                Array(1){android.Manifest.permission.READ_CONTACTS},
                100)
        }
        else
        {
            //if permission granted then access user contacts
            readUserContacts();
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
    {
        //This function is called
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 100 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            //if permission granted
            //then display contacts
            readUserContacts()
        }
    }
    private fun readUserContacts()
    {
        var source = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER

        ).toTypedArray()

        var destination = intArrayOf(android.R.id.text1, android.R.id.text2)

        var result_set = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            contact_fields, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

        var adapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, result_set,
            source, destination, 0);

        list_view.adapter = adapter



    }
}