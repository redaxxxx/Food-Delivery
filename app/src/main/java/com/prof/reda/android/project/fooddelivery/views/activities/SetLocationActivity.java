package com.prof.reda.android.project.fooddelivery.views.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.Tasks;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.PlaceAutocompleteAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.ActivitySetLocationBinding;
import com.prof.reda.android.project.fooddelivery.models.PlaceDataModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetLocationActivity extends AppCompatActivity implements OnMapReadyCallback,
GoogleApiClient.OnConnectionFailedListener{

    //    private static final String TAG = SetLocationActivity.class.getSimpleName();
//    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = SetLocationActivity.class.getSimpleName();
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40,-168),
            new LatLng(71,136));

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private ActivitySetLocationBinding binding;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1468;
    private static final float DEFAULT_ZOOM = 15f;
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private PlaceDetectionClient mPlaceDetectionClient;
    private GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter placeAdapter;

    private PlaceDataModel mPlace;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_location);

        getLocationPermission();
        init();

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getDevicesLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private void init(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        placeAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);
        binding.autoCompleteEditText.setAdapter(placeAdapter);

//        binding.autoCompleteEditText.setOnItemClickListener(mAutoCompleteClickListener);

        binding.autoCompleteEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                keyEvent.getAction() == KeyEvent.KEYCODE_ENTER ||
                keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    //execute our method for searching

                    geoLocate();
                }
                return false;
            }

        });
    }

    private void geoLocate() {
        Log.d(TAG, "goeLocate");
        String searchText = binding.autoCompleteEditText.getText().toString();
        Geocoder geocoder = new Geocoder(SetLocationActivity.this);
        List<Address> addressList = new ArrayList<>();

        try {

            addressList = geocoder.getFromLocationName(searchText, 1);
        }catch (IOException e){
            Log.d(TAG, "geoLocate: IOException " + e.getMessage());
        }

        if (addressList.size() > 0){
            Address address = addressList.get(0);

            Log.d(TAG, "GoeLocate: " + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }

    private void getDevicesLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation = (Location) task.getResult();
                            if (currentLocation != null){
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                        DEFAULT_ZOOM, "My Location");
                            }else {
                                Log.d(TAG, "current location is null");
                            }

                        } else {
                            Log.d(TAG, "onComplete: task current location is null");
                            Toast.makeText(SetLocationActivity.this, "onComplete: current location is null", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        } catch (SecurityException e) {
            Log.d(TAG, "get device location: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: move camera to lat " + latLng.latitude + ", lng" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")){
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.addMarker(options);
        }

//        hideSoftKeyboard();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapLocation);
        mapFragment.getMapAsync(SetLocationActivity.this);
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    // initialize our map
                    initMap();
                }
        }
    }


    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onResume() {
        super.onResume();
        ActionBar supportActionBar = ((AppCompatActivity) this).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ActionBar supportActionBar = ((AppCompatActivity) this).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

//    private AdapterView.OnItemClickListener mAutoCompleteClickListener =
//            new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    hideSoftKeyboard();
//                    final AutocompletePrediction item = placeAdapter.getItem(position);
//                    final String placeId = item.getPlaceId();
//
//                    Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);
//                    placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback);
//                }
//            };

//    private PlaceBufferResponse places;
//    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback =
//            new OnCompleteListener<PlaceBufferResponse>() {
//                @Override
//                public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
//                    if (!task.isSuccessful()){
//                        places = task.getResult();
//                        Log.d(TAG, "SetLocationActivity: task is not complete" + places);
//                        places.release();
//                        return;
//                    }
//
//                    final Place place = places.get(0);
//
//                    try {
//                        mPlace = new PlaceDataModel();
//                        mPlace.setName(place.getName().toString());
//                        mPlace.setAddress(place.getAddress().toString());
//                        mPlace.setPlaceId(place.getId());
//                        mPlace.setAttributions(place.getAttributions().toString());
//                        mPlace.setLatLng(place.getLatLng());
//                        mPlace.setPhoneNumber(place.getPhoneNumber().toString());
//                        mPlace.setWebsiteUri(place.getWebsiteUri());
//                        Log.d(TAG, "Place: " + mPlace.toString());
//
//                    }catch (NullPointerException e){
//                        Log.d(TAG, "NullPointerException" + e.getMessage());
//                    }
//
//                    moveCamera(mPlace.getLatLng(), DEFAULT_ZOOM, mPlace.getName());
//
//                    places.release();
//                }
//            };




    //    private boolean isServicesOk(){
//        Log.d(TAG, "isServicesOk: checking google services version");
//
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
//                SetLocationActivity.this);
//        if (available == ConnectionResult.SUCCESS){
//            //everything is fine and user can make map request
//            Log.d(TAG, "isServicesOk: Google play services is working");
//            return true;
//        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
//            // an error occurred but user resolved it
//            Log.d(TAG, "isServicesOk: error occurred but we can resolved it");
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available,
//                    ERROR_DIALOG_REQUEST);
//            dialog.show();
//        } else {
//            Toast.makeText(this, "we can't make map request", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
}