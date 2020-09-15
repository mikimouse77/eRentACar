package android.fit.ba.rentacar;

import android.content.DialogInterface;
import android.fit.ba.rentacar.data.UserPostVM;
import android.fit.ba.rentacar.helper.MyApiRequest;
import android.fit.ba.rentacar.helper.MyRunnable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText phone;
    private EditText email;
    private EditText username;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        username = findViewById(R.id.userName);
        pass = findViewById(R.id.password);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnRegistracijaClick();
            }
        });
    }

    private void do_btnRegistracijaClick() {
        if (validate()) {
            UserPostVM model = new UserPostVM();
            model.FirstName = firstName.getText().toString();
            model.LastName = lastName.getText().toString();
            model.Email = email.getText().toString();
            model.Phone = phone.getText().toString();
            model.UserName = username.getText().toString();
            model.PasswordSalt = pass.getText().toString();

            MyApiRequest.post(this, "api/Users", model, new MyRunnable<UserPostVM>() {
                @Override
                public void run(UserPostVM x) {
                    Toast.makeText(Register.this, "Uspjesna registracija.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    private boolean validate() {

        AlertDialog adb = new AlertDialog.Builder(Register.this).create();
        adb.setTitle("Greška");
        adb.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if (firstName.getText().toString().length() < 3) {
            adb.setMessage("Ime treba da sadrzi minimalno 3 karaktera.");
            adb.show();
            return false;
        }

        if (lastName.getText().toString().length() < 3) {
            adb.setMessage("Prezime treba da sadrzi minimalno 3 karaktera.");
            adb.show();
            return false;
        }

        if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() == false) {
            adb.setMessage("Email nije u ispravnom formatu.");
            adb.show();
            return false;
        }

        if (phone.getText().toString().length() < 6) {
            adb.setMessage("Telefon treba da sadrzi minimalno 6 karaktera.");
            adb.show();
            return false;
        }

        if (username.getText().toString().length() < 4) {
            adb.setMessage("Korisnicko ime treba da sadrzi minimalno 4 karaktera.");
            adb.show();
            return false;
        }

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(pass.getText().toString());

        if (matcher.matches() == false) {
            adb.setMessage("Lozinka treba sadrzavat minimalno 6 karaktera, najmanje jedan broj i kombinaciju malih/velikih slova.");
            adb.show();
            return false;
        }

        return true;
    }
}
