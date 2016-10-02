package com.decodar.bluetoothConnection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.Log;

import com.decodar.seeder.R;

import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by malith on 9/17/16.
 */
public class BConnection {

    public String decodeFromUUID(String uuid){
        String ans="";
        String regex = "([a-f0-9]{8})-([a-f0-9]{4})-4([a-f0-9]{3})-8([a-f0-9]{3})-([a-f0-9]{8})c0de";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(uuid.toLowerCase());
        if (m.find()) {
            String hexData = m.group(1) + m.group(2) + m.group(3) + m.group(4) +
                    m.group(5);

            for (int i=0; i<26; i+=2){
                //Log.d("Decoding ", hexData.substring(i,i+2));
                int substring = Integer.parseInt(hexData.substring(i,i+2), 16);

                if(substring != 0)
                    ans = ans + (char)substring;
            }
            return ans;
        } else {
            return null;
        }
    }

    public String encodeDatatoUUID(String msg){
        String data = msg;
        String paddedData = String.format("%-14s", data);
        String hexData =
                String.format("%028x", new BigInteger(1,
                        paddedData.getBytes()));
        String uuidString =
                hexData.substring(0, 8) + "-" +
                        hexData.substring(8, 12) + "-4" +
                        hexData.substring(12, 15) + "-8" +
                        hexData.substring(15, 18) + "-" +
                        hexData.substring(18, 26) + "c0de";

        return uuidString;
    }

}
