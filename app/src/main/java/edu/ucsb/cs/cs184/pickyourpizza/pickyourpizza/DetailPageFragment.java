package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Kari on 12/14/17.
 */

public class DetailPageFragment extends Fragment implements OnMapReadyCallback {

    View view;
    GoogleMap mMap;
    MapView mapView;

    String businessName;

    FragmentHelper activityCallback;

    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mFusedLocationProviderClient;

    public static Context context;

    boolean mLocationPermissionGranted = false;
    Location mLastKnownLocation;
    Locale mLocale;
    double currentLatitude;
    double currentLongitude;

    LatLng mDefaultLocation = new LatLng(34.412936, -119.847863);
    float DEFAULT_ZOOM = 14.0f;

    public static final int REQUEST_LOCATION_ID = 5;

    //  LatLng of home 34.170234,-118.462094

    public interface FragmentHelper {
        public void changeFragment(String newFragment, boolean forward);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (FragmentHelper) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentHelper");
        }
    }

    public DetailPageFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(getContext(), null);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getContext(), null);
        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_page_fragment, container, false);

        Button backButton = (Button) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCallback.changeFragment("ListViewFragment", false);
            }
        });

        //set index used to get the correct information through the data structures
        final int index = getIndexOfBusiness();
        //Set Business name of selected business from List View
        TextView pizzaPlaceTextView = (TextView) view.findViewById(R.id.pizzaPlaceTextView);
        if (businessName != null) {
            pizzaPlaceTextView.setText(businessName);
        }

        TextView phoneNumber = (TextView) view.findViewById(R.id.phoneNumTextView);
        phoneNumber.setText(BuildPizzaListTask.business.get(index).getPhoneNumber());
        //set listener to listen for User tapping on phone number to make call
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //start up phone app (dial pad) and pass in business phone number
                    Intent phoneCall = new Intent(Intent.ACTION_DIAL);
                    phoneCall.setData(Uri.parse("tel:"+BuildPizzaListTask.business.get(index).getPhoneNumber().replaceAll("\\D+","")));
                    startActivity(phoneCall);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        TextView toppingsAvailable = (TextView) view.findViewById(R.id.toppingsAvailTextView);
        toppingsAvailable.setText("Options available: " +BuildPizzaListTask.options_Available.get(index).toString().replaceAll("(^\\[|]$)", ""));

        TextView toppingsNotAvailable = (TextView) view.findViewById(R.id.toppingsNotAvailTextView);
        toppingsNotAvailable.setText("Options not available: " +BuildPizzaListTask.options_Not_Available.get(index).toString().replaceAll("(^\\[|]$)", ""));

        TextView recommendedSizes = (TextView) view.findViewById(R.id.pizzaSizesTextView);
        //if X-Large is not available then replace recommended Sizes with Large sized pizzas
        if(BuildPizzaListTask.options_Not_Available.get(index).toString().contains("X-Large")) {
            recommendedSizes.setText("Recommended Sizes: "+ ListViewFragment.listOfSizes.replaceAll("X-", ""));
        }
        else{
            recommendedSizes.setText("Recommended Sizes: " + ListViewFragment.listOfSizes);
        }

        TextView price = (TextView) view.findViewById(R.id.priceTextView);
        price.setText(String.format("Price: $%.2f",BuildPizzaListTask.options_Available_Price.get(index)));


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        startPlacePicker();

    }

    private void startPlacePicker()
    {
        PlacePicker.IntentBuilder placePicker = new PlacePicker.IntentBuilder();

        try{
            //Intent startPlacePicker = placePicker.build(getActivity());
            startActivityForResult(placePicker.build(getActivity()),1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == 1 && resultCode == RESULT_OK){
            Place selectPlace = PlacePicker.getPlace(getContext(),data);

            TextView pizzaPlaceTextView = (TextView) view.findViewById(R.id.pizzaPlaceTextView);
            pizzaPlaceTextView.setText(selectPlace.getName());
            Log.i("PLACE NAME", "PLACE: "+selectPlace.getName());

            TextView phoneNumber = (TextView) view.findViewById(R.id.phoneNumTextView);
            phoneNumber.setText(selectPlace.getPhoneNumber());
            Log.i("PHONE NUMBER", "PHONE NUMBER: " + selectPlace.getPhoneNumber());

        }
    }

    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_ID);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case REQUEST_LOCATION_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s3", e.getMessage());
        }
    }


    private void getDeviceLocation() {
    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.i("CURRENT LOCATION: ", task.getResult().toString());
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location)task.getResult();
                            currentLatitude = mLastKnownLocation.getLatitude();
                            currentLongitude = mLastKnownLocation.getLongitude();
                            Log.i("CURRENT LOCATION", "Current Latitude: " + currentLatitude + " Current Longitude: " + currentLongitude);
                        } else {
                            Log.d("getDeviceLocation", "Current location is null. Using defaults.");
                            Log.e("getDeviceLocation", "Exception: %s2", task.getException());
                            //default is UCSB Lat,Long
                            currentLatitude = 34.412936;
                            currentLongitude = -119.847863;
                            /*
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                            */
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    public void setBusinessName(String businessName) {
        this.businessName = businessName;
        Log.i("DetailP/setBusinessName","THIS IS THE SELECTED BUSINESS NAME " + businessName);
    }


    //loops through the business list and checks to see if name matches selected item from ListView
    public int getIndexOfBusiness(){
        for(int i = 0; i < MainActivity.businessList.size(); i++){
            if(MainActivity.businessList.get(i).getName().equals(businessName)){
                return i;
            }
        }
        Log.i("DetailP/getIndexOfBusin", "NO INDEX RETURNED. NO MATCHES TO BUSINESS FOUND");
        return -1;
    }

}