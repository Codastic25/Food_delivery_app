package com.example.food_delivery_marvilliers_aurian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ALL BUTTONS
        Button finihOrderButton = findViewById(R.id.mybutton);

        /*
        RadioButton cockatils = findViewById(R.id.cocktailFruitFrais),
        RadioButton vin = findViewById(R.id.vinrouge),
        RadioButton burger = findViewById(R.id.hamburgerXXL),
        RadioButton pizza = findViewById(R.id.pizzaSavoyarde),
        RadioButton raclette = findViewById(R.id.raclette),
        RadioButton moules = findViewById(R.id.moules),
        RadioButton mojito = findViewById(R.id.mojito),
        RadioButton orangina = findViewById(R.id.orangina)
         */


        // button click listener action
        finihOrderButton.setOnClickListener ( new View.OnClickListener () {

            //when I click on finish my order --> calculate the total price and print it to the next page, so open the next page (if no food selected : error message)
            @Override
            public void onClick ( View view ) {
                calculatePrice();
            }
        });
    }

    private void calculatePrice() {

        // liste repas en récupérant les radiobutton par leurs id
        RadioButton[] repas = {
                findViewById(R.id.cocktailFruitFrais),
                findViewById(R.id.vinrouge),
                findViewById(R.id.hamburgerXXL),
                findViewById(R.id.pizzaSavoyarde),
                findViewById(R.id.raclette),
                findViewById(R.id.moules),
                findViewById(R.id.mojito),
                findViewById(R.id.orangina)
        };

        double prix_total = 0.0;
        boolean isAnyItemChecked = false;

        for (RadioButton item : repas) {
            if (item.isChecked()) {
                isAnyItemChecked = true; // Un élément est bien sélectionné
                Double price = Double.parseDouble(item.getTag().toString());//je convertis les prix des Radiobutton en double
                prix_total += price;//calcul du prix total
            }
        }

        if (!isAnyItemChecked) {
            Toast.makeText(getApplicationContext(), "Please select at least one meal/drink", Toast.LENGTH_SHORT).show();
        }
        else {
            //lance la deuxieme activity "menuDetails"
            Intent intent = new Intent(MainActivity.this, menuDetails.class);
            intent.putExtra("prix_total", prix_total); // Ajouter la valeur dans l'Intent
            startActivity(intent);
        }
    }
}