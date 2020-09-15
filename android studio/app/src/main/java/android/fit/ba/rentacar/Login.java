package android.fit.ba.rentacar;

import android.content.DialogInterface;
import android.content.Intent;
import android.fit.ba.rentacar.data.AuthenticationLoginPostVM;
import android.fit.ba.rentacar.data.AuthenticationResultVM;
import android.fit.ba.rentacar.data.Global;
import android.fit.ba.rentacar.helper.MyApiRequest;
import android.fit.ba.rentacar.helper.MyRunnable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.userName);
        txtPassword = findViewById(R.id.password);

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnPrijavaClick();
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private void do_btnPrijavaClick() {
        if (validate()) {
            String strUserName = txtUsername.getText().toString();
            String strPassword = txtPassword.getText().toString();

            AuthenticationLoginPostVM model = new AuthenticationLoginPostVM(strUserName, strPassword);

            MyApiRequest.get(this, "api/Authentication/LoginCheck/" + strUserName + "/" + strPassword, new MyRunnable<AuthenticationResultVM>() {
                @Override
                public void run(AuthenticationResultVM x) {
                    checkLogin(x);
                }
            });
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
            alertDialog.setTitle("Greska");
            alertDialog.setMessage("Unesite korisnicko ime i lozinku.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    private void checkLogin(AuthenticationResultVM x) {
        if ("PogresniPodaci".equals(x.FirstName)) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Pogrešno korisničko ime/lozinka.", Snackbar.LENGTH_LONG).show();
        } else {
            Global.AuthenticatedUser = x;
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private boolean validate() {
        if (txtUsername.getText().toString().isEmpty())
            return false;
        if (txtPassword.getText().toString().isEmpty())
            return false;

        return true;
    }

    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
