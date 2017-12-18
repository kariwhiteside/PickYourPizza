package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Loading... on 12/17/17.
 */

public class GoogleMapAsynTask extends AsyncTask<Void,Void,Void> {

    private double currentLatitude;
    private double currentLongitude;
    private String search;
    private GoogleMap mMap;
    private List<Address> addressList;

    public GoogleMapAsynTask(double currentLatitude, double currentLongitude, String search, GoogleMap mMap,List<Address> addressList){
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.search = search;
        this.mMap = mMap;
        this.addressList = addressList;
    }

    @Override
    protected Void doInBackground(Void...params) {
        //create a geocoder, an object that converts a street address to coordinates
        Geocoder geocoder = new Geocoder(DetailPageFragment.context, Locale.getDefault());
        int attempts = 0;
        //while the addresslist contains nothing keep querying/waiting for search result
        while ((addressList == null || addressList.size() == 0) && attempts != 10){
            attempts++;
            Log.i("launchMap", Geocoder.isPresent() + " IS BACK-END SERVICE PRESENT");
            Log.i("launchMap", "businessName: " + search);
            try {
                //MODIFY THE LOCATION NAME UNTIL A RESULT POPS UP FOR ALL BUSINESS PLACES
                //CONCATENATE A STRING THAT HELPS NARROW DOWN SEARCH RESULT
                //DOES NOT WORK
                addressList = geocoder.getFromLocationName(search + " Food " + currentLatitude + "," + currentLongitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //addressList has been loaded with an address
            Log.i("launchMap", "addressList: " + addressList);
        }
        return null;
    }


    protected void onProgressUpdate(Void... progress) {

    }

    @Override
    protected void onPostExecute(Void v) {
        //check to see if address actually contains an address
        if (addressList != null && addressList.size() != 0) {
            //get the address and its coordinates
            Address address = addressList.get(0);
            LatLng businessLocation = new LatLng(address.getLatitude(), address.getLongitude());

            //update and focus google map on address
            float zoomLevel = 14.0f; //This goes up to 21
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(businessLocation, zoomLevel));
            mMap.addMarker(new MarkerOptions().position(businessLocation).title(address.getAddressLine(0) + address.getAddressLine(1) + address.getAddressLine(2)));
        }
        else {
            //default point to UCSB
            Log.i("GoogleMap/onPostExecute", "Resulting to Default: UCSB");
            LatLng UCSB = new LatLng(currentLatitude, currentLongitude);
            float zoomLevel = 14.0f; //This goes up to 21
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(UCSB, zoomLevel));
            mMap.addMarker(new MarkerOptions().position(UCSB).title(search));
        }
    }

}
