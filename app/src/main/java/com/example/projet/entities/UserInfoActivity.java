package com.example.projet.entities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.R;
import com.example.projet.database.database;
import com.example.projet.database.User;
import com.example.projet.ui.CertSpecialityActivity;
import com.example.projet.ui.SmokingQuestionsActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserInfoActivity extends AppCompatActivity {

    EditText editNom, editPrenom, editEmail, editPhone, editUserName, editPassword;
    TextInputEditText editTextBirthdate;
    Button buttonNext;
    RadioGroup radioGroupRole;
    private database db;
    private Intent intent;
    private int age = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        db = database.getInstance(getApplicationContext());

        editNom = findViewById(R.id.editTextNom);
        editPrenom = findViewById(R.id.editTextPrenom);
        editEmail = findViewById(R.id.editTextEmail);
        editPhone = findViewById(R.id.editTextPhone);
        editUserName = findViewById(R.id.editTextUserName);
        editPassword = findViewById(R.id.editTextPassword);
        editTextBirthdate = findViewById(R.id.editTextBirthdate);
        radioGroupRole = findViewById(R.id.radioGroupRole);
        buttonNext = findViewById(R.id.buttonNext);

        editTextBirthdate.setOnClickListener(v -> showDatePicker());

        buttonNext.setOnClickListener(v -> {
            String password = editPassword.getText().toString().trim();
            String nom = editNom.getText().toString().trim();
            String prenom = editPrenom.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String userName = editUserName.getText().toString().trim();
            String birthDateStr = editTextBirthdate.getText().toString().trim();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || phone.isEmpty() || userName.isEmpty() || password.isEmpty() || birthDateStr.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            age = calculateAgeFromString(birthDateStr);
            if (age <= 0) {
                Toast.makeText(this, "Invalid birth date", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = radioGroupRole.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRole = findViewById(selectedId);
            String role = selectedRole.getText().toString().toLowerCase();

            User user = new User();
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setEmail(email);
            user.setNumTel(phone);
            user.setUserName(userName);
            user.setAge(age);
            user.setPassword(null); // if you're storing password later, update this

            switch (role) {
                case "user":
                    user.setCoach(false);
                    user.setUser(true);
                    user.setMedecin(false);
                    intent = new Intent(this, SmokingQuestionsActivity.class);
                    break;
                case "doctor":
                    user.setCoach(false);
                    user.setUser(false);
                    user.setMedecin(true);
                    intent = new Intent(this, CertSpecialityActivity.class);
                    break;
                case "coach":
                    user.setCoach(true);
                    user.setUser(false);
                    user.setMedecin(false);
                    intent = new Intent(this, CertSpecialityActivity.class);
                    break;
                default:
                    return;
            }

            intent.putExtra("currentUser", user);
            startActivity(intent);
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH);
        int dayNow = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String dateFormatted = String.format(Locale.US, "%02d/%02d/%04d", month + 1, dayOfMonth, year);
            editTextBirthdate.setText(dateFormatted);
        }, yearNow, monthNow, dayNow);

        datePickerDialog.show();
    }

    private int calculateAgeFromString(String birthdateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        try {
            Date birthDate = sdf.parse(birthdateString);
            if (birthDate == null) return 0;

            Calendar birthCal = Calendar.getInstance();
            birthCal.setTime(birthDate);

            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < birthCal.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
