package ru.smak.gpstestjava;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PermissionManager {
    private final AppCompatActivity activity;
    public PermissionManager(AppCompatActivity activity){
        this.activity = activity;
    }
    void requestPermissions(String[] permissions,
                            int requestCode){
        ArrayList<String> absentPermissionsList = new ArrayList<>();
        for (String permission: permissions) {
            if (!hasPermission(activity, permission)) absentPermissionsList.add(permission);
        }
        String[] absentPermissions = new String[absentPermissionsList.size()];
        absentPermissionsList.toArray(absentPermissions);
        activity.requestPermissions(absentPermissions, requestCode);
    }



    private static boolean hasPermission(Context context, String permission){
        return context.checkSelfPermission(
             permission
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public void onSetPermission(int requestCode, String[] permissions, int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++){
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                if (activity.shouldShowRequestPermissionRationale(permissions[i])){
                    new AlertDialog.Builder(activity)
                            .setCancelable(true)
                            .setMessage("Ну очень надо!")
                            .setNegativeButton("Все равно нет!", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton("Ну ладно, уж!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    requestPermissions(new String[]{permissions[i]}, requestCode);
                                }
                            })
                            .setTitle("Слыш, бро!")
                            .create()
                            .show();
                }
            }
        }
    }
}
