package com.myneighbourhood.Velin_Kerkov;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.myneighbourhood.R;

public class CustomMapFragment extends Fragment implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap googleMap;

    public static CustomMapFragment newInstance() {
        CustomMapFragment fragment = new CustomMapFragment();
        return fragment;
    }

    public CustomMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) v.findViewById(R.id.fragment_map_MV_map);

        mapView.onCreate(savedInstanceState);

        mapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double homeLat = 55.8734611;
        double homeLong = -4.2890117;
        double differenceLat = 0.002;
        double differenceLong = 0.004;

        LatLng homeLatLong = new LatLng(homeLat, homeLong);
        MarkerOptions home = new MarkerOptions().position(homeLatLong).title("Home");
        googleMap.addMarker(home);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(homeLatLong).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));


        PolygonOptions neighbourhoodPolygon = new PolygonOptions().add(
                new LatLng(homeLat - differenceLat, homeLong + differenceLong),
                new LatLng(homeLat - differenceLat, homeLong - differenceLong),
                new LatLng(homeLat + differenceLat, homeLong - differenceLong),
                new LatLng(homeLat + differenceLat, homeLong + differenceLong),
                new LatLng(homeLat - differenceLat, homeLong + differenceLong)
        ).strokeWidth(2).fillColor(ContextCompat.getColor(this.getActivity(), R.color.lightTransparentGreen));

        Polygon polygon = googleMap.addPolygon(neighbourhoodPolygon);
    }
}
