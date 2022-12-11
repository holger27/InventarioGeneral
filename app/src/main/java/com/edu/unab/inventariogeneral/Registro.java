package com.edu.unab.inventariogeneral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
Button botRegistrar ;
EditText edTxNombre,edTxEmail,edTxContraseña;

FirebaseAuth auth = FirebaseAuth.getInstance();
FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edTxEmail = findViewById(R.id.edTxEmail);
        edTxContraseña = findViewById(R.id.edTxContraseña);
        edTxNombre = findViewById(R.id.edTxNombre);
        botRegistrar =findViewById(R.id.botRegistrar);


        botRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edTxEmail.getText().toString();
                String ctr = edTxContraseña.getText().toString();
                String nom = edTxNombre.getText().toString();
                registrar(email,ctr,nom);


            }
        });

    }
    public void registrar(String email,String contraseña,String nom){
        auth.createUserWithEmailAndPassword(email,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = auth.getCurrentUser().getUid();
                    Map<String, Object>  datos =new HashMap<>();
                    datos.put("email",email);
                    datos.put("contraseña",contraseña);

                    firestore.collection("usuarios").document(id).set(datos).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Registro.this,"registrado",Toast.LENGTH_SHORT).show();


                            }else{
                                Toast.makeText(Registro.this,"Problema",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });



    }

}