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

    public static Context context;

    float DEFAULT_ZOOM = 14.0f;

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

    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(getContext(), null);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getContext(), null);
        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    }
    */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_page_fragment, container, false);

        //changes current fragment to previous fragment
        Button backButton = (Button) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCallback.changeFragment("ListViewFragment", false);
            }
        });

        //set index used to get the correct information through the data structures
        final int index = getIndexOfBusiness();

        TextView phoneNumber = (TextView) view.findViewById(R.id.phoneNumTextView);
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

        startPlacePicker();

    }

    //start Google Places UI as an activity
    private void startPlacePicker()
    {
        PlacePicker.IntentBuilder placePicker = new PlacePicker.IntentBuilder();

        try{
            startActivityForResult(placePicker.build(getActivity()),1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Waits for user to select place on Google Places UI before exiting activity and returning to fragment
    //Returns info about the selected place which is used to set Text Fields
    //Animates camera
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == 1 && resultCode == RESULT_OK){
            Place selectPlace = PlacePicker.getPlace(getContext(),data);

            TextView pizzaPlaceTextView = (TextView) view.findViewById(R.id.pizzaPlaceTextView);
            pizzaPlaceTextView.setText(selectPlace.getName());
            Log.i("BUSINESS NAME", "PLACE: "+selectPlace.getName());

            TextView pizzaAddressTextView = (TextView) view.findViewById(R.id.addressTextView);
            pizzaAddressTextView.setText(selectPlace.getAddress());
            Log.i("BUSINESS PHONE NUMBER", "BUSINESS:" +selectPlace.getAddress());

            TextView phoneNumber = (TextView) view.findViewById(R.id.phoneNumTextView);
            phoneNumber.setText(selectPlace.getPhoneNumber());
            Log.i("PHONE NUMBER", "PHONE NUMBER: " + selectPlace.getPhoneNumber());

            //animate camera to business coordinates on Google Map fragment
            //places marker at coordinates
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectPlace.getLatLng(), DEFAULT_ZOOM));
            mMap.addMarker(new MarkerOptions().position(selectPlace.getLatLng()).title(selectPlace.getName().toString()));

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