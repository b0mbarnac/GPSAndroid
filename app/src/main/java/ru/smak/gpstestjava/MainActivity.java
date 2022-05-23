package ru.smak.gpstestjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import ru.smak.gpstestjava.location.LocationHelper;

public class MainActivity extends AppCompatActivity {

    private final PermissionManager pm = new PermissionManager(this);
    LinearLayout linearLayout ;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        pm.requestPermissions(permissions, 0);
        button = findViewById(R.id.startButton);
        TextView pastTitle = new TextView(this);
        pastTitle.setTextSize(33);
        pastTitle.setText(getResources().getString(R.string.pastTitle));
        pastTitle.setLayoutParams(new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        pastTitle.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        linearLayout = findViewById(R.id.main_con);
        linearLayout.addView(pastTitle);

        LocationHelper.mutableLocation.observe(this, value -> {
            TextView text = new TextView(this);
            text.setText(String.format(Locale.getDefault(), "%s", value));
            text.setTextSize(27);
            text.setLayoutParams(new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(text);
        });
        button.setOnClickListener(oclBtnOk);
    }

    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pm.onSetPermission(requestCode, permissions, grantResults);
    }

    OnClickListener oclBtnOk = new OnClickListener() {
        @Override
        public void onClick(View v) {
            LocationHelper locHelper = new LocationHelper();
            locHelper.setActivity(MainActivity.this);
            LocationHelper.startLocationListening(v.getContext(), locHelper);
        }
    };
}