package com.example.food_delivery_marvilliers_aurian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class menuDetails extends AppCompatActivity {

    private boolean isInputValid = true;
    private boolean isPaymentValid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_payment);

        // Récupération de l'Intent et du prix total (variable qui navigue entre mes 2 Activity)
        Intent intent = getIntent();
        double prix_total = intent.getDoubleExtra("prix_total", 0.0); // 0.0 est la valeur par défaut

        // Récupération du TextView où afficher le prix
        TextView prixTextView = findViewById(R.id.prix_total_textview);
        prixTextView.setText("Total Price: " + prix_total + "€");

        Button confirmOrder = findViewById(R.id.confirm_button);

        // button click listener action
        confirmOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Désactiver le bouton pour éviter les clics multiples pendant le traitement
                confirmOrder.setEnabled(false);

                // Réinitialiser les variables de validation
                isInputValid = true;
                isPaymentValid = true;

                // Vérification des champs d'entrée
                verifyInputFields();

                // Vérification du moyen de paiement (seulement si les champs sont valides)
                if (isInputValid) {
                    verifyPayment();
                }

                // Si tout est valide, on peut passer à l'étape suivante (par exemple, naviguer vers la prochaine activité)
                if (isInputValid && isPaymentValid) {
                    Toast.makeText(getApplicationContext(), "Everything is good", Toast.LENGTH_SHORT).show();
                }

                // Réactiver le bouton après le traitement
                confirmOrder.setEnabled(true);
            }
        });

        Button back = findViewById(R.id.back_main);

        // button click listener action pour back
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menuDetails.this, MainActivity.class);
                startActivity(intent);

                // Optionnel : si tu veux fermer la page actuelle (menuDetails) et ne pas revenir en arrière
                finish();
            }
        });

        //appel de la fonction showKeyboard pour mes EditText qui sont des input
        EditText nameInput = findViewById(R.id.nom_client);
        EditText addresse = findViewById(R.id.addresse);
        EditText heure_delivery = findViewById(R.id.time_delivery);

        // Événement sur le clic pour afficher le clavier
        nameInput.setOnClickListener(v -> showKeyboard(nameInput));
        addresse.setOnClickListener(v -> showKeyboard(addresse));
        heure_delivery.setOnClickListener(v -> showKeyboard(heure_delivery));

    }

    // Méthode pour vérifier la méthode de paiement
    public void verifyPayment() {
        //j'avais fais un tableau de ces RadioButton
        // RadioButton pour les méthodes de paiement
        RadioButton visa = findViewById(R.id.Visa);
        RadioButton mastercard = findViewById(R.id.MasterCard);
        RadioButton paypal = findViewById(R.id.Paypal);

        // Vérifier si une méthode de paiement est sélectionnée
        if (visa.isChecked() || mastercard.isChecked() || paypal.isChecked()) {
            isPaymentValid = true; // Moyen de paiement valide
            Toast.makeText(getApplicationContext(), "Payment method selected", Toast.LENGTH_SHORT).show();
        } else {
            isPaymentValid = false;
            Toast.makeText(getApplicationContext(), "Please select at least one payment method", Toast.LENGTH_SHORT).show();
        }
    }

    // Méthode pour vérifier les champs de saisie
    public void verifyInputFields() {
        // EditText pour les détails de la livraison
        EditText[] details = {
                findViewById(R.id.nom_client),
                findViewById(R.id.addresse),
                findViewById(R.id.time_delivery)
        };

        for (EditText item : details) {
            String inputText = item.getText().toString().trim(); // Récupère le texte des saisies de l'utilisateur
            if (inputText.isEmpty()) {
                isInputValid = false;
                Toast.makeText(getApplicationContext(), "Please fill the delivery details", Toast.LENGTH_SHORT).show();
                break; // Stoppe la boucle dès qu'un champ est vide
            }
        }
    }

    //afficher le clavier, event par java, fonction trouvée sur internet
    public void showKeyboard(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

}
