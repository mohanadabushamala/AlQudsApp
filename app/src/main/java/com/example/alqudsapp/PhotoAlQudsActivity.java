package com.example.alqudsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PhotoAlQudsActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    List<Image> list;

    RecyclerView imageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_al_quds);

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        imageRecyclerView = findViewById(R.id.imageRecyclerView);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        imageRecyclerView.setLayoutManager(manager);

        CollectionReference reference = firestore.collection("Image_Al_Quds");
        Task<QuerySnapshot> q = reference.get();
        q.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    Image image = queryDocumentSnapshot.toObject(Image.class);
                    list.add(image);
                }
                ImageAdapter adapter = new ImageAdapter(getApplicationContext(), list);
                imageRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

    }
}