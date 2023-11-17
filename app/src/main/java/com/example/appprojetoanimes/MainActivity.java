package com.example.appprojetoanimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.Normalizer;
import java.util.regex.Pattern;



import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    private ProgressDialog progressDialog;
    private int qualSolicitacao = 1;
    RecyclerView myRecyclerview;
    AnimeAdapter myAdapter;
    String busca = "";

    private List<Object> dados = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerview = (RecyclerView) findViewById(R.id.id_recycler_view);

        myRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerview.setLayoutManager(layoutManager);

        lerJSON();
    }

    public void busca(View view){
        EditText busca = (EditText) findViewById(R.id.busca);
        this.busca = busca.getText().toString();
        lerJSON();
    }
    public void todos(View view){
        this.busca = "";
        lerJSON();
    }
    public void lerJSON(){
        qualSolicitacao = 1;
        if(checkInternetConection()){
            Log.i("Metodo carregado","LerJSON");
            progressDialog = ProgressDialog.show(this,"","Obtendo Dados");
            new DownloadJson().execute("https://www.jsonkeeper.com/b/PO8M");
        } else {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Sem conexão. Verifique.", Toast.LENGTH_LONG).show();
        }
    }

    public static String semAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public void addJson(String strjson, String busca) {
        dados.clear();
        Log.i("Metodo carregado","addJson");
        try {
            JSONObject objRaiz = new JSONObject(strjson);
            JSONArray jsonArray = objRaiz.optJSONArray("anime");
            JSONObject jsonObject = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.optString("id");
                String nome = jsonObject.optString("nome");
                String temporada = jsonObject.optString("temporadas");
                String episodios = jsonObject.optString("episodios");
                String generos = jsonObject.optString("generos");
                String ano = jsonObject.optString("data");
                String material = jsonObject.optString("material_original");
                String status = jsonObject.optString("status");

                final Collator instance = Collator.getInstance();
                instance.setStrength(Collator.PRIMARY);

                String generoSemAcento = semAcento(generos).toLowerCase();
                String nomeLower = nome.toLowerCase();

                Anime anime = new Anime(id, nome, temporada, episodios, generos, ano, material, status);

                if (busca.equals("")){
                    dados.add(anime);
                } else {
                    if (nomeLower.contains(busca.toLowerCase()) || generoSemAcento.contains(busca.toLowerCase())) {
                        dados.add(anime);
                    }
                }
            }
        } catch (JSONException e) {
            Log.d("funcionario","inserido");
        } finally {
            progressDialog.dismiss();
            myAdapter = new AnimeAdapter(getApplicationContext(), dados);
            myRecyclerview.setAdapter(myAdapter);
        }
    }

    public boolean checkInternetConection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private class DownloadJson extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // params é um vetor onde params[0] é a URL
            try {
                return downloadJSON(params[0]);
            } catch (IOException e) {
                return "Erro";
            }
        }

        // onPostExecute exibe o resultado do AsyncTask
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("Erro")) {
                progressDialog.dismiss();
                return;
            }
            if (qualSolicitacao == 1) {
                addJson(result, busca);
            }
            else {
                addJson(result,busca);
            }
        }

        private String downloadJSON(String myurl) throws IOException {
            InputStream is = null;
            String respostaHttp = "Erro";
            HttpURLConnection conn = null;
            InputStream in = null;
            ByteArrayOutputStream bos = null;
            try {
                URL u = new URL(myurl);
                conn = (HttpURLConnection) u.openConnection();
                conn.setConnectTimeout(4000); // 4 segundos de timeout
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                in = conn.getInputStream();
                bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    bos.write(buffer, 0, len);
                }
                respostaHttp = bos.toString("UTF-8");
            } catch (Exception ex) {
                respostaHttp = "Erro";
            } finally {
                if (in != null) in.close();
            }
            return respostaHttp;
        }
    }
}