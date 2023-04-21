package com.example.galeriadefotosparcial;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_SELECT_IMAGE = 100;

    ImageButton whats, correoG, galeriaF, mostrarF;
    private Uri mSelectedImagedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correoG = findViewById(R.id.correo);
        whats = findViewById(R.id.mensaje);
        galeriaF = findViewById(R.id.galeria);
        mostrarF = findViewById(R.id.mostrar);

        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectedImagedUri ==null){
                    Toast.makeText(MainActivity.this, "Elige una foto", Toast.LENGTH_SHORT).show();
                } else {
                    SendImageViaWhatsApp(mSelectedImagedUri);
                }
        }

    });
        correoG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectedImagedUri == null){
                    Toast.makeText(MainActivity.this, "Elige una foto", Toast.LENGTH_SHORT).show();
                } else {
                    SendImageviaEmail (mSelectedImagedUri);
                }
            }
        });
        galeriaF.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_SELECT_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null){
            mSelectedImagedUri = data.getData();
            mostrarF.setImageURI(mSelectedImagedUri);
            whats.setEnabled(true);
            correoG.setEnabled(true);
        }
    }

    private void SendImageViaWhatsApp(Uri imageUri){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(intent, "Compartir imagen"));
    }
    private void SendImageviaEmail(Uri imageUri){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Compartir imagen"));
}
}