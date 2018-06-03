package domain.novasa;


import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment2 extends Fragment {

     List<Objectss> objList;

    ListView spinner;
    ArrayAdapter<Objectss> adapts;

    int cachesize = 10 * 1024 * 1024;


    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_fragment2, container, false);

        final Cache cache = new Cache(getActivity().getCacheDir(), cachesize);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if(!isNetworkAvailable()) {
                            int maxStale = 60 * 60 * 24 * 28;
                            request = request
                                    .newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }return chain.proceed(request);
                    }
                }).build();



        spinner = (ListView) v.findViewById(R.id.spinner);
        objList = new ArrayList<>();


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.Base_Url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        Call<Objectss> call = api.getObj();

        call.enqueue(new Callback<Objectss>() {
            @Override
            public void onResponse(Call<Objectss> call, Response<Objectss> response) {

                    for(Objectss obj: response.body().getProducts())
                    {
                        Objectss o = new Objectss(obj.getId(), obj.getName());
                        objList.add(o);
                    }
                adapts = new Adapter_listview(getContext(),R.layout.listviewlay,objList);
                spinner.setAdapter(adapts);

                Log.d("CAche" , okHttpClient.toString());
            }

            @Override
            public void onFailure(Call<Objectss> call, Throwable t) {

                Log.d("Tag", t.toString());
            }
        });


        Button tilføjbtn = (Button) v.findViewById(R.id.tilføjbtn);
        tilføjbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflaterr = getLayoutInflater(null);
                View alertLayout = inflaterr.inflate(R.layout.addlayout, null);
                dialog.setTitle("Tilføj Produkt");
                dialog.setView(alertLayout);
                final EditText editText = (EditText) alertLayout.findViewById(R.id.TilføjEdit);
                dialog.setCancelable(false);
                dialog.setNegativeButton("Annuller", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                dialog.setPositiveButton("Tilføj", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Random r = new Random();
                        int i = r.nextInt();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(API.Base_Url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        Objectss product = new Objectss(String.valueOf(i), editText.getText().toString());


                        API api = retrofit.create(API.class);
                        Call<Objectss> call = api.addObj(product);
                        call.enqueue(new Callback<Objectss>() {
                            @Override
                            public void onResponse(Call<Objectss> call, Response<Objectss> response) {
                                Toast.makeText(getContext(), "Produktet blev tilføjet", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Objectss> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                AlertDialog dialog2 = dialog.create();
                dialog2.show();
            }
        });



        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Integer slet_id;
                slet_id = Integer.parseInt(adapts.getItem(position).getId());
                final AlertDialog slet_dialog = new AlertDialog.Builder(getContext()).create();
                slet_dialog.setTitle("Advarsel");
                slet_dialog.setMessage("Er du sikker på du vil slette dette produkt?");
                slet_dialog.setCancelable(false);
                slet_dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        API api = retrofit.create(API.class);
                        Call<Objectss> call_slet = api.deleteObj(slet_id.toString());
                        call_slet.enqueue(new Callback<Objectss>() {
                            @Override
                            public void onResponse(Call<Objectss> call, Response<Objectss> response) {
                                Toast.makeText(getContext(), "Produktet er blevet slettet", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Objectss> call, Throwable t) {
                                Toast.makeText(getContext(), "Fejl, noget gik galt", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                slet_dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Nej", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        slet_dialog.hide();
                    }
                });

                slet_dialog.show();
            }
        });


        return v;
    }

  public boolean isNetworkAvailable(){
          ConnectivityManager connectivityManager
                  = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
          NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
          return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
  }
}
