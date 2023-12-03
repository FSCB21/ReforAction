package com.example.ejemploreto1_04;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejemploreto1_04.Models.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class UserRegisterActivity extends AppCompatActivity {

    Button register;

    EditText name, email, phone, password, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        register= findViewById(R.id.buttonR);

        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        phone = findViewById(R.id.etPhone);
        password = findViewById(R.id.etPassword);
        password2 = findViewById(R.id.etPassword2);




        Intent inicio= new Intent(getApplicationContext(),
                MainActivity.class);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUser()){
                    User user = createrUser();
                    saveUser(user);

                    Toast.makeText(getApplicationContext(),"Registro Exitoso", Toast.LENGTH_LONG).show();

                    System.out.println("OK!");
                    try {
                        sleep(500);
                        startActivity(inicio);
                        finish();
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"ALgunos de los campos estan vacios", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public boolean validateUser(){
        boolean validate= true;

        setNormalState();

        if(name.getText().toString().isEmpty()){
            setErrorState(name);
            validate=false;
        }
        if (email.getText().toString().isEmpty()){
            setErrorState(email);
            validate=false;
        }
        if (phone.getText().toString().isEmpty()){
            setErrorState(phone);
            validate=false;
        }
        if (password.getText().toString().isEmpty()){
            setErrorState(password);
            validate=false;
        }
        if (password2.getText().toString().isEmpty()){
            setErrorState(password2);
            validate=false;
        }
        if (!password.getText().toString().equals(password2.getText().toString())){
            setErrorState(password);
            setErrorState(password2);
            validate=false;
        }

        return validate;
    }


    public User createrUser(){
        String id,nameUser,emailUser,phoneUser,passwordUser;
        nameUser= name.getText().toString();
        id=generateID(nameUser);
        emailUser=email.getText().toString();
        phoneUser=phone.getText().toString();
        passwordUser=password.getText().toString();
        User user= new User(id,nameUser,emailUser,phoneUser,passwordUser);

        return user;
    }

    public String generateID(String name){
        String id="";
        for (int i=0;i<2;i++){
            int letra= (int) (Math.random()*name.length());
            int number= (int)(Math.random()*2000);
            id+=name.charAt(letra);
            id+=number;
        }
        return id;
    }

    private void setErrorState(EditText obj){
        obj.setBackgroundColor(Color.rgb(235,77, 30));
    }

    private void setNormalState(){
        name.setBackgroundColor(Color.TRANSPARENT);
        email.setBackgroundColor(Color.TRANSPARENT);
        phone.setBackgroundColor(Color.TRANSPARENT);
        password.setBackgroundColor(Color.TRANSPARENT);
        password2.setBackgroundColor(Color.TRANSPARENT);
    }

    public void saveUser(User user){

        File fileUser= new File(getFilesDir(),"userData.txt");

        try {
            FileWriter writer=new FileWriter(fileUser,true);
            BufferedWriter bufferedWriter= new BufferedWriter(writer);
            bufferedWriter.write(
                    user.getID()+";"+
                        user.getName()+";"+
                        user.getEmail()+";"+
                        user.getPhone()+";"+
                        user.getPassword()
            );
            bufferedWriter.newLine();

            bufferedWriter.close();
        }catch (Exception error){
            error.printStackTrace();
        }

    }

}