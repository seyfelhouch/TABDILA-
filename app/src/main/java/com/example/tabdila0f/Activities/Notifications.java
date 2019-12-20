package com.example.tabdila0f.Activities;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Notifications extends Activity {

    public Notifications(){

    }

    public void notificationFunction(Context context, String type , String message){

        switch (type)
        {
            case "toast":
               Toast.makeText(context, message , Toast.LENGTH_SHORT).show();
                break;
            case "notification":
                //createNotification(message , context);
                break;
        }

    }


}
