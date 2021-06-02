package com.example.consumirrestconretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consumirrestconretrofit.Interface.RevistasUteq;
import com.example.consumirrestconretrofit.Model.j_id;
import com.google.gson.JsonIOException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private EditText edtxtId;
    private ImageView imgFind;
    private TextView edtxtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edtxtId=(EditText) findViewById(R.id.dtxTypeId);
        imgFind=(ImageButton) findViewById(R.id.imgBtnFind);
        edtxtInfo=(TextView) findViewById(R.id.txtvInfo);

        edtxtInfo.setMovementMethod(new ScrollingMovementMethod());
        System.out.println("TODOOOOO BIEEEN");

        imgFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCover();
            }
        });

    }

    private void searchCover(){
        String url="https://revistas.uteq.edu.ec/ws/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RevistasUteq revistaUtq=retrofit.create(RevistasUteq.class);
        Call<List<j_id>> call =revistaUtq.getRevistas(edtxtId.getText().toString());
        call.enqueue(new Callback<List<j_id>>() {
            @Override
            public void onResponse(Call<List<j_id>> call, Response<List<j_id>> response) {
                showCoverText(response.body());
            }

            @Override
            public void onFailure(Call<List<j_id>> call, Throwable t) {
                edtxtInfo.setText("Error al cargar: No existe info sobre ese ID");
            }
        });
    }
    private void showCoverText(List<j_id> jID){

        edtxtInfo.setText("");
        try{
        for (j_id id:jID){

            edtxtInfo.append("ID: "+id.getIssue_id()+"\n");
            edtxtInfo.append("VOLUMEN: "+id.getVolume()+"\n");
            edtxtInfo.append("NÚMERO: "+id.getNumber()+"\n");
            edtxtInfo.append("AÑO: "+id.getYear()+"\n");
            edtxtInfo.append("FECHA DE PUBLICACIÓN: "+id.getDate_published()+"\n");
            edtxtInfo.append("TITULO: "+id.getTitle()+"\n");
            edtxtInfo.append("URL: "+id.getDoi()+"\n");
            edtxtInfo.append("IMAGE: "+id.getCover()+"\n");
            edtxtInfo.append("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n");
        }}
        catch (JsonIOException ex)
        {
            Toast.makeText(this,"Error al cargar lista: "+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}