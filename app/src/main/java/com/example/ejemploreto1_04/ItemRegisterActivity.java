package com.example.ejemploreto1_04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ejemploreto1_04.Models.Plantacion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ItemRegisterActivity extends AppCompatActivity {

    Button registrarButton;
    Spinner mesPlantacionSpinner, tipoPlantacionSpinner;
    EditText cantidadET, costoET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_register);

        registrarButton = findViewById(R.id.buttonR);
        mesPlantacionSpinner = findViewById(R.id.spinnerMonth);
        tipoPlantacionSpinner = findViewById(R.id.spinnerType);
        cantidadET = findViewById(R.id.etCantidad);
        costoET = findViewById(R.id.etCostoUnidad);

        Intent receive= getIntent();
        String idUser= receive.getStringExtra("idUser");

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()){
                    NewPlantacion(idUser);
                    Toast.makeText(getApplicationContext(),"Registro exitoso",
                            Toast.LENGTH_LONG).show();
                    cleanView();
                }

            }
        });
    }

    public void NewPlantacion(String idUser){
        int cantidadPlantada = Integer.parseInt(cantidadET.getText().toString());
        int precioUnitario = Integer.parseInt(costoET.getText().toString());
        String mesPlantacion = mesPlantacionSpinner.getSelectedItem().toString();
        String tipoPlantacion = tipoPlantacionSpinner.getSelectedItem().toString();
        String idPlantacion = idUser + "_" + mesPlantacion + "_" + tipoPlantacion;
        Plantacion newPlantacionObj = new Plantacion(idPlantacion, cantidadPlantada, precioUnitario, mesPlantacion, tipoPlantacion, idUser);
        newPlantacionEntry(newPlantacionObj);
    }


    public void newPlantacionEntry (Plantacion plant){

        File plantacionFile = new File(getFilesDir(), "plantacionData.txt");

        try {
            FileWriter fw = new FileWriter(plantacionFile, true);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write(
                    plant.getID()+";"+
                    plant.getMonth()+";"+
                    plant.getType()+";"+
                    plant.getQuantity()+";"+
                    plant.getUnitPrice()+";"+
                    plant.getIdUser()
            );
            bf.newLine();
            bf.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean validateData(){
        boolean stateValidation = false;

        if (
            mesPlantacionSpinner.getSelectedItem().toString().isEmpty()||
            tipoPlantacionSpinner.getSelectedItem().toString().isEmpty()||
            cantidadET.getText().toString().isEmpty()||
            costoET.getText().toString().isEmpty()
        ){
            Toast.makeText(getApplicationContext(), "Todos los campos deben estar diligenciados", Toast.LENGTH_SHORT).show();
        }else {
            stateValidation = true;
        }

        return stateValidation;
    }


    public void cleanView(){
        cantidadET.setText("");
        costoET.setText("");
        mesPlantacionSpinner.setSelection(0);
        tipoPlantacionSpinner.setSelection(0);
    }
}