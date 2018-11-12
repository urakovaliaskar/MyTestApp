package com.aliaskarurakov.android.mytestapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddUser extends AppCompatActivity {

    TextInputEditText name, surname, age;
    MaterialButton addBtn;
    String tempName, tempSurname, tempAge;
    String token;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (TextInputEditText)findViewById(R.id.name);
        surname = (TextInputEditText)findViewById(R.id.surname);
        age = (TextInputEditText)findViewById(R.id.age);
        addBtn = (MaterialButton)findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(name.getText().toString(),
                        surname.getText().toString(),
                        age.getText().toString());
            }
        });

    }

    public void registerUser(String name, String surname, String age) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("name", name)
                .add("surname", surname)
                .add("age", age)
                .build();

        Request request = new Request.Builder()
                .url("http://aliaskar.000webhostapp.com/test/register.php")
                .post(body)
                .build();

        /*try {
            client.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Toast.makeText(getApplicationContext(),"User successfully added!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });

    }
/*
    public void InsertData(final String name, final String surname,final String age, final String token){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                String NameHolder = name ;
                String SurnameHolder = surname ;
                String AgeHolder = age;
                String TokenHolder = token;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("firstname", NameHolder));
                nameValuePairs.add(new BasicNameValuePair("surname", SurnameHolder));
                nameValuePairs.add(new BasicNameValuePair("age", AgeHolder));
                nameValuePairs.add(new BasicNameValuePair("token", TokenHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("https://aliaskar.000webhostapp.com/adduser.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(AddUser.this, result, Toast.LENGTH_LONG).show();
                //getJSON("http://aliaskar.000webhostapp.com/adduser.php");
                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle("New User: "+name+" was added!")
                        .setContentText(name+" "+surname+" "+age+"years old.")
                            .setSmallIcon(R.drawable.ic_add).build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(name, surname, age);
    }

    public void getData(){
        tempName = name.getText().toString();
        tempSurname = surname.getText().toString();
        tempAge = age.getText().toString();
    }


    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                Log.e("Check", s);

            }

            @Override
            protected String doInBackground(Void... voids) {

                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }
        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
*/
}
