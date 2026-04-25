//package com.example.smartspot;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.smartspot.api.ApiClient;
//import com.example.smartspot.api.ApiService;
//import com.example.smartspot.model.User;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import android.content.Intent;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        ApiService apiService = ApiClient.getClient().create(ApiService.class);
//        getSharedPreferences("USER", MODE_PRIVATE)
//                .edit()
//                .putInt("user_id", 1)   // pretend logged in user
//                .apply();
//        startActivity(new Intent(this, PastBookingsActivity.class));
//
//
//        apiService.getUsers().enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<User> users = response.body();
//
//                    for (User user : users) {
//                        Log.d("API_TEST", "Name: " + user.getFullName());
//                        Log.d("API_TEST", "Email: " + user.getEmail());
//                    }
//                } else {
//                    Log.d("API_TEST", "Response failed");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Log.e("API_TEST", "Error: " + t.getMessage());
//            }
//        });
//    }
//}
package com.example.smartspot;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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
