package com.example.hp.goodease;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity implements OnItemSelectedListener {
private Button Sign_out;
    private TextView welcome;
    private Spinner ddsize;
    private TextView est_time, est_price ;
    private Button order, reset;
    public static int price;
    private FirebaseAuth firebaseAuth;
    public String Latitude_pick;
   public String Longitude_pick;
    public String Longitude_drop;
    public String Latitude_drop;
    public static String dur_dis[] = new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("bt bc", " oncreate" );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        welcome =(TextView)findViewById(R.id.tvwelcome);
        est_price = (TextView)findViewById( R.id.tv_price );
        est_time = (TextView)findViewById( R.id.tv_time );
        order =(Button)findViewById( R.id.btn_placeorder );
        reset =(Button)findViewById( R.id.btn_reset );
        ddsize = (Spinner)findViewById(R.id.sp_size);
        Sign_out = (Button)findViewById(R.id.btnsign_out);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        welcome.setText("WELCOME "+user.getEmail());
        Log.d("bt bc", " sab ban gaya" );

      /* est_time.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                est_time.setText( "Estimated Time: "+dur_dis[1]);

            }
        } );*/

      //  price = 15.0*dur_dis[0].+2.0*dur_dis[1]+30.0;
        //est_price.setText( "Estimated Price: "+price+"$" );
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.size));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddsize.setAdapter(myAdapter);
        reset.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( SecondActivity.this, LocationActivity.class ) );
            }
        } );
        order.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity( new Intent( SecondActivity.this, TrackActivity.class ) );
                String user_ID = firebaseAuth.getCurrentUser().getUid();
                Latitude_pick = Double.toString( LocationActivity.latLng_pick.latitude );
                Longitude_pick = Double.toString( LocationActivity.latLng_pick.longitude );
                Latitude_drop = Double.toString( LocationActivity.latLng_drop.latitude );
                Longitude_drop = Double.toString( LocationActivity.latLng_drop.longitude );

                BWPlaceOrder bwPlaceOrder = new BWPlaceOrder( SecondActivity.this );
                bwPlaceOrder.execute( "place order", user_ID, Latitude_pick, Longitude_pick, Latitude_drop, Latitude_drop, Integer.toString( price ) );
                //Intent intent = new Intent( SecondActivity.this, TrackActivity.class );
                //startActivity( intent );

            }
        } );


        Sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(SecondActivity.this, Login.class));
            }
        });
        ddsize.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String itemSelected = adapterView.getItemAtPosition(i).toString();
        String copy[] = new String[2];
        copy[0] = dur_dis[0];
        copy[1]= dur_dis[1];
        if(copy[0] != null && copy[1] !=null) {
            String trip_distance = copy[0].substring( 0, (copy[0].indexOf( "." ) ) );
            String trip_duration = copy[1].substring( 0, (copy[1].indexOf( "m" ) - 1) );
            Log.d( "sub string", trip_distance );
            int time = 0, distance = 0;
            for (int j = trip_distance.length() - 1; j >= 0; j--) {
                distance += Math.pow( 10, trip_distance.length() - j - 1 ) *( (int) trip_distance.charAt( j )-48);


            }
            for (int j = trip_duration.length() - 1; j >= 0; j--) {
                time += Math.pow( 10, trip_duration.length() - j - 1 ) * ( (int) trip_duration.charAt( j )-48);


            }
            Log.d( " converted stringto int", "bc" );
            switch (i) {
                case 1:
                    price = 15 * distance + 2 * time + 30;
                    break;
                case 2:
                    price = 15 * distance + 2 * time + 60;
                    break;
                case 3:
                    price = 15 * distance + 2 * time + 100;
                    break;
                default:
                    break;
            }
            Log.d( "price calculated", trip_duration );
            est_time.setText( "Estimated trip Time: " +copy[1] );
            Log.d( " time aa raha h", copy[1] );
            est_price.setText( "Estimated Price: " +price);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
