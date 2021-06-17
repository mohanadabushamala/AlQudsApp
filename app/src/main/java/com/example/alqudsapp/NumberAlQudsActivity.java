package com.example.alqudsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class NumberAlQudsActivity extends AppCompatActivity {

    TextView eventAlQudsTV;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_al_quds);

        eventAlQudsTV = findViewById(R.id.eventAlQudsTV);

        firestore = FirebaseFirestore.getInstance();

        CollectionReference reference1 = firestore.collection("eventInAlQuds");
        Task<QuerySnapshot> q1 = reference1.get();
        q1.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {

                    Image t = queryDocumentSnapshot.toObject(Image.class);
                    eventAlQudsTV.setText(t.getImage());

                }
            }
        });



    }
}