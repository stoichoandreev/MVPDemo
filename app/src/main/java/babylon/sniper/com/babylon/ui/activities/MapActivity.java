package babylon.sniper.com.babylon.ui.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import babylon.sniper.com.babylon.R;
import babylon.sniper.com.babylon.api.object_models.User;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    public static final String TAG = MapActivity.class.getCanonicalName();
    public static final String USER_AVATAR_URL = "avatar_url";

    @Bind(R.id.title_tv)            TextView titleTextView;
    @Bind(R.id.user_name)           TextView userName;
    @Bind(R.id.name)                TextView name;
    @Bind(R.id.user_email)          TextView userEmail;
    @Bind(R.id.user_phone)          TextView userPhone;
    @Bind(R.id.user_website)        TextView userWebsite;
    @Bind(R.id.user_street)         TextView userStreet;
    @Bind(R.id.user_avatar)         SimpleDraweeView userAvatar;

    private                         User selectedUser;
    private                         String userAvatarUrl;
    private                         LatLng userLocation;

    @Override
    protected int getLayoutResource() {
        return R.layout.map_activity_layout;
    }

    @Override
    protected void applicationHasInternetConnection() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedUser = extras.containsKey(StartActivity.SELECTED_USER) ? (User) extras.getSerializable(StartActivity.SELECTED_USER) : User.EMPTY;
            userAvatarUrl = extras.containsKey(USER_AVATAR_URL) ? extras.getString(USER_AVATAR_URL) : "";
            try {
                final double lat = Double.parseDouble(selectedUser.address.geo.lat);
                final double lng = Double.parseDouble(selectedUser.address.geo.lng);
                userLocation = new LatLng(lat,lng);
            }catch (NumberFormatException ex){
                ex.printStackTrace();
                //Those are London coordinates
                userLocation = new LatLng(51.50,0.125);
            }
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initUi();
    }

    private void initUi() {
        titleTextView.setText(getResources().getString(R.string.user_details_screen_title));
        userName.setText(selectedUser.username);
        name.setText(selectedUser.name);
        userEmail.setText(selectedUser.email);
        userWebsite.setText(selectedUser.website);
        userPhone.setText(selectedUser.phone);
        userStreet.setText(selectedUser.address.street);
        userAvatar.setImageURI(Uri.parse(userAvatarUrl));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) {
            Snackbar.make(titleTextView, "Sorry! unable to create maps", Snackbar.LENGTH_SHORT).show();
        }else {
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);

            // Updates the location and zoom of the MapView
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLocation, 10);
            googleMap.animateCamera(cameraUpdate);
            addUserLocation(googleMap);
        }
    }

    private void addUserLocation(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(userLocation)
                .title("This User Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        );
    }
}
