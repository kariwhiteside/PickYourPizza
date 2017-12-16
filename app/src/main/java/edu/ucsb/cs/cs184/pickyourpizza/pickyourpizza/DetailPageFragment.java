package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kari on 12/14/17.
 */

public class DetailPageFragment extends Fragment implements OnMapReadyCallback {

    View view;
    GoogleMap map;
    MapView mapView;

    String businessName;

    FragmentHelper activityCallback;

    int requestLocationId = 5;

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

        TextView pizzaPlaceTextView = (TextView) view.findViewById(R.id.pizzaPlaceTextView);
        if (businessName != null) {
            pizzaPlaceTextView.setText(businessName);
        }


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
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            launchMap();
        } else {
            ActivityCompat.requestPermissions((MainActivity) getContext(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, requestLocationId);
        }


    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void launchMap() {

        List<Address> addressList = null;
        if (businessName != null && !businessName.equals("")) {
            Geocoder geocoder = new Geocoder(getContext());
            Log.i("launchMap", "businessName: " + businessName);
            try {
                addressList = geocoder.getFromLocationName(businessName, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("launchMap", "addressList: " + addressList);

            if (addressList != null && addressList.size() != 0) {
                Address address = addressList.get(0);
                LatLng businessLocation = new LatLng(address.getLatitude(), address.getLongitude());

                // LatLng UCSB = new LatLng(34.412936, -119.847863);
                float zoomLevel = 14.0f; //This goes up to 21
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(businessLocation, zoomLevel));
                map.addMarker(new MarkerOptions().position(businessLocation).title(address.getAddressLine(0) +
                        address.getAddressLine(1) + address.getAddressLine(2)));
            } else {
                LatLng UCSB = new LatLng(34.412936, -119.847863);
                float zoomLevel = 14.0f; //This goes up to 21
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(UCSB, zoomLevel));
                map.addMarker(new MarkerOptions().position(UCSB).title(businessName));
            }

        }

    }
}
