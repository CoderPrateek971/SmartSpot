
package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartspot.api.ApiClient;
import com.example.smartspot.api.ApiService;
// FIXED: Importing User from the same package as it is not in a 'model' folder in your tree
//import com.example.smartspot.User;
import com.example.smartspot.model.User;
//import com.google.firebase.firestore.auth.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnGoToBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // simulate login
        getSharedPreferences("USER", MODE_PRIVATE)
                .edit()
                .putInt("user_id", 1)
                .apply();

        // open past bookings screen
        startActivity(new Intent(this, PastBookingsActivity.class));
    }
}
