package com.example.ejemploreto1_04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ejemploreto1_04.Models.Plantacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    TextView totalUnidadesPlantadas, totalInvertido, totalArbolNativo, totalArbolRapido, totalPlantaEndemica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadistics);

        totalUnidadesPlantadas = findViewById(R.id.textViewTotalUnits);
        totalInvertido = findViewById(R.id.textViewTotalCost);
        totalArbolNativo = findViewById(R.id.textViewTypeAQuantity);
        totalArbolRapido = findViewById(R.id.textViewTypeBQuantity);
        totalPlantaEndemica = findViewById(R.id.textViewTypeCQuantity);

        Intent receive= getIntent();
        String idUser= receive.getStringExtra("idUser");

        File plantacionFile = new File(getFilesDir(),"plantacionData.txt");

        ArrayList<Plantacion> plantacionData = listPlantacion(plantacionFile, idUser);

        int totalArbolNativoValue = totalizarPorTipo(plantacionData, "Árbol Nativo");
        int totalArbolRapidoValue = totalizarPorTipo(plantacionData, "Árbol Rapido Crecimiento");
        int totalPlantaEndemicaValue = totalizarPorTipo(plantacionData, "Planta Endémica PE");

        int costoArbolNativoValue = totalizarCostoPorTipo(plantacionData, "Árbol Nativo");
        int costoArbolRapidoValue = totalizarCostoPorTipo(plantacionData, "Árbol Rapido Crecimiento");
        int costoPlantaEndemicaValue = totalizarCostoPorTipo(plantacionData, "Planta Endémica PE");

        totalArbolNativo.setText(totalArbolNativoValue + " UND");
        totalArbolRapido.setText(totalArbolRapidoValue + " UND");
        totalPlantaEndemica.setText(totalPlantaEndemicaValue + " UND");

        totalUnidadesPlantadas.setText((totalArbolNativoValue + totalArbolRapidoValue + totalPlantaEndemicaValue) + " UND");
        totalInvertido.setText("$" + (costoArbolNativoValue + costoArbolRapidoValue + costoPlantaEndemicaValue) + "COP");
    }

    public int totalizarPorTipo (ArrayList<Plantacion> lsPlantacion,String tipoATotalizar) {
        int total = 0;
        for (Plantacion e : lsPlantacion){
            if (e.getType().equals(tipoATotalizar)){
                total+=e.getQuantity();
            }
        }
        return total;
    }

    public int totalizarCostoPorTipo (ArrayList<Plantacion> lsPlantacion,String tipoATotalizar) {
        int total = 0;
        for (Plantacion e : lsPlantacion){
            if (e.getType().equals(tipoATotalizar)){
                total+= (e.getUnitPrice() * e.getQuantity());
            }
        }
        return total;
    }


    public ArrayList<Plantacion> listPlantacion(File plantacionFL, String idUser){
        ArrayList<Plantacion> list= new ArrayList<>();
        try {
            FileReader fr= new FileReader(plantacionFL);
            BufferedReader br=new BufferedReader(fr);

            String cadena;

            while ((cadena=br.readLine())!=null){
                String [] data= cadena.split(";");
                if(data[5].equals(idUser)){
                    String ID = data[0];
                    int quantity = Integer.parseInt(data[3]);
                    int unitPrice = Integer.parseInt(data[4]);
                    String month = data[1];
                    String type = data[2];

                    Plantacion obj = new Plantacion(ID, quantity, unitPrice, month, type, idUser);
                    list.add(obj);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}