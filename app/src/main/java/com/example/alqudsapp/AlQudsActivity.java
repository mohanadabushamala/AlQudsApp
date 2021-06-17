package com.example.alqudsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AlQudsActivity extends AppCompatActivity {

    VideoView videoView;
    TextView alQudesTextTV;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_al_quds);

        firestore = FirebaseFirestore.getInstance();

        videoView = findViewById(R.id.videoView);
        alQudesTextTV = findViewById(R.id.alQudesTextTV);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        mediaController.setAnchorView(videoView);

        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/chatapp-db313.appspot.com/o/uploads%2F%D9%85%D8%A7%D9%84%D8%A7%20%D8%AA%D8%B9%D8%B1%D9%81%D9%87%20%D8%B9%D9%86%20_%20%D8%A7%D9%84%D9%85%D8%B3%D8%AC%D8%AF%20%D8%A7%D9%84%D8%A7%D9%82%D8%B5%D9%89%20%D8%A7%D9%88%D9%84%D9%89%20%D8%A7%D9%84%D9%82%D8%A8%D9%84%D8%AA%D9%8A%D9%86.mp4?alt=media&token=a4d63a68-d78b-4221-acaa-200b8d22545c");
        videoView.setVideoURI(uri);
        videoView.start();

        CollectionReference reference1 = firestore.collection("AlQuds");
        Task<QuerySnapshot> q1 = reference1.get();
        q1.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {

                    Image t = queryDocumentSnapshot.toObject(Image.class);
                    alQudesTextTV.setText(t.getImage());

                }
            }
        });


    }
}