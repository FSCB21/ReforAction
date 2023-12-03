package com.example.ejemploreto1_04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ejemploreto1_04.Models.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button login;
    TextView register;

    EditText usuarioLogin, passwordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioLogin = findViewById(R.id.etUsuarioLogin);
        passwordLogin = findViewById(R.id.etPasswordLogin);


        login=findViewById(R.id.button);
        register=findViewById(R.id.textView4);

        Intent home= new Intent(getApplicationContext(),
                HomeActivity.class);
        Intent registrarUsuario= new Intent(getApplicationContext(),
                UserRegisterActivity.class);


        File fileUser = new File(getFilesDir(), "userData.txt");

        ArrayList<User> usersData = loadUsers(fileUser);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registrarUsuario);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean loginState = false;
                String userID = "";
                System.out.println(usersData);
                if (usuarioLogin.getText().toString().isEmpty()|| passwordLogin.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Todos los campos deben estar diligenciados",Toast.LENGTH_LONG).show();
                }else{
                    String userLoginT = usuarioLogin.getText().toString();
                    for (User e:usersData){
                        if (
                            e.getName().equals(userLoginT) ||
                            e.getEmail().equals(userLoginT) ||
                            e.getPhone().equals(userLoginT)
                        ){
                            if (e.getPassword().equals(passwordLogin.getText().toString())){
                                loginState = true;
                                userID = e.getID();
                                break;
                            }
                        }
                    }
                }

                if (loginState){
                    home.putExtra("idUser",userID);
                    startActivity(home);
                }else{
                    Toast.makeText(getApplicationContext(),"Usuario No registrado y/o contraseña erronea",Toast.LENGTH_LONG).show();
                }
            }
        });



    }


    public ArrayList<User> loadUsers(File dataFile){
        ArrayList<User> list= new ArrayList<>();

        try {
            FileReader reader= new FileReader(dataFile);
            BufferedReader br= new BufferedReader(reader);
            String user;

            while ((user=br.readLine())!=null){
                String[] userData= user.split(";");
                String id= userData[0];
                String name= userData[1];
                String email=userData[2];
                String phone= userData[3];
                String password= userData[4];

                User userObject= new User(id,name,email,phone,password);
                list.add(userObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}