import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmPassword, etPhone;
    Spinner spVehicle;
    Button btnSignup;

    String[] vehicleTypes = {"Bike", "Car", "SUV"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhone = findViewById(R.id.etPhone);
        spVehicle = findViewById(R.id.spVehicle);
        btnSignup = findViewById(R.id.btnSignup);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, vehicleTypes);
        spVehicle.setAdapter(adapter);

        btnSignup.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String vehicle = spVehicle.getSelectedItem().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        sendDataToServer(name, email, password, phone, vehicle);
    }

    private void sendDataToServer(String name, String email, String password, String phone, String vehicle) {

        String url = "http://10.7.34.70/signup";

        JSONObject json = new JSONObject();
        try {
            json.put("full_name", name);
            json.put("email", email);
            json.put("password", password);
            json.put("phone", phone);
            json.put("vehicle", vehicle);
        } catch (Exception e) {}

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json,
                response -> {
                    Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}