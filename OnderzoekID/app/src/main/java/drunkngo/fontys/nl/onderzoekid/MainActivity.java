package drunkngo.fontys.nl.onderzoekid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private TextView lbl_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lbl_result = (TextView) findViewById(R.id.lbl_result);
    }

    public void onClickBtnTele(View v)
    {
        checkReadPhoneStatePermission();
    }

    public void onClickBtnMAC(View v)
    {
        chechWifiManagerPermission();
    }

    public void onClickBtnsecureID(View v)
    {
        lbl_result.setText(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
    }

    public void onClickBtnUUID(View v)
    {
        lbl_result.setText(UUID.randomUUID().toString());
    }

    public static final int MY_PERMISSIONS_REQUEST_PHONE_STATE = 99;
    public static final int MY_PERMISSIONS_REQUEST_WIFI_SERVICE = 100;

    public boolean checkReadPhoneStatePermission() {
        String deviceID = "DENIED";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_PHONE_STATE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_PHONE_STATE);
            }
            return false;
        } else {
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            deviceID = manager.getDeviceId();

            return true;
        }
    }

    public boolean chechWifiManagerPermission()
    {
        String deviceID = "DENIED";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_WIFI_STATE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                        MY_PERMISSIONS_REQUEST_WIFI_SERVICE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, MY_PERMISSIONS_REQUEST_WIFI_SERVICE);
            }
            return false;
        } else {

            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            String address = info.getMacAddress();
            lbl_result.setText(address);
            return true;
        }
    }

    public boolean checkSecureAndroidIDPermission()
    {
        String deviceID = "DENIED";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_WIFI_STATE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                        MY_PERMISSIONS_REQUEST_WIFI_SERVICE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, MY_PERMISSIONS_REQUEST_WIFI_SERVICE);
            }
            return false;
        } else {

            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            String address = info.getMacAddress();
            lbl_result.setText(address);
            return true;
        }
    }



    //Wordt aangeroepen na een permissie request
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        String deviceID = "DENIED";

        //Check waarover de request ging
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_PHONE_STATE: {
                //Check of er een resultaat is
                if (grantResults.length > 0
                        //Check of dit permissie gegeven is
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Voer uit
                        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                        deviceID = manager.getDeviceId();
                    }
                }
                else
                    {
                    //Permissie niet toegestaaan, toon dit.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }
            case MY_PERMISSIONS_REQUEST_WIFI_SERVICE:
            {
                //Check of er een resultaat is
                if (grantResults.length > 0
                        //Check of dit permissie gegeven is
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_WIFI_STATE)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Voer uit
                        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiInfo info = manager.getConnectionInfo();
                       deviceID = info.getMacAddress();

                    }
                }
                else
                {
                    //Permissie niet toegestaaan, toon dit.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
        lbl_result.setText(deviceID);
        return;
    }



}
