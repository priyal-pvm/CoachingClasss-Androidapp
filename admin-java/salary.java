package com.admin.spzone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class salary extends AppCompatActivity {

            Button button;
            EditText text;

//            String SECRET_KEY = "sk_test_51MoXFlSIGSPZ7RJFgISBfTF4xpOsSwaycLA5n494yGQyRWHGU5pLFLqUTOqbGQXYyAF6oKt5fNSpUNopMsUsbL4I00OlZ3Zn6V";
//            String PUBLISH_KEY = "pk_test_51MoXFlSIGSPZ7RJFA26dSitILLd12CNGOkaNeiNIGcgrhK4x3LCuhnVxv3g9aeJSELOeqkvUjwLFNpIYQFh5qji100Q4Lq1u6j";
            String SECRET_KEY = "sk_test_51Mqk8GSBt2znxfkH7Pkjfc3NfHPHv8KFu71Oiw325Yzl0TTyj49Cvo1CpFvdyCNHaqNk12cdgQqiOGj9q9wcrBjP00zxktNp2k";
            String PUBLISH_KEY ="pk_test_51Mqk8GSBt2znxfkHgRqVexN5oYk68pVrWLfvP6ME7cb2ctsALRgM4zSZkPPbjASSLlWsDgLt5Z8EuFE9SvVh3PFh001lYDAOT5";





            PaymentSheet paymentSheet;

            //    String amounttext
            String customerID;
            String Ephericalkey;
            String ClientSecret;



                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_salary);


                    button = findViewById(R.id.pay);
//        final String amounttxt = text.getText().toString();

                text = findViewById(R.id.amount);


                //notification start


                //notification end


                PaymentConfiguration.init(this, PUBLISH_KEY);


                paymentSheet = new PaymentSheet(this, paymentSheetResult -> {

                    onPaymentResualt(paymentSheetResult);

                });


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Paymentflow();
                    }
                });


                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "https://api.stripe.com/v1/customers",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject object = new JSONObject(response);
                                    customerID = object.getString("id");
                                    Toast.makeText(salary.this, customerID, Toast.LENGTH_SHORT).show();

                                    getEphericalkey(customerID);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header = new HashMap<>();
                        header.put("Authorization", "Bearer " + SECRET_KEY);
                        return header;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(salary.this);
                requestQueue.add(stringRequest);


            }

            private void onPaymentResualt(PaymentSheetResult paymentSheetResult) {

                if (paymentSheetResult instanceof PaymentSheetResult.Completed) {


                    Toast.makeText(salary.this, "payment done", Toast.LENGTH_SHORT).show();
                    //notification start
//
//            Intent intent = new Intent(getApplicationContext(), leaveRetrieveData.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//            PendingIntent pendingintent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_ONE_SHOT);
//
//
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(fees.this, "Leave")
//                    .setSmallIcon(R.drawable.notification)
//                    .setContentTitle("Fees")
//                    .setContentText(" Fees Paid ")
//                    .setAutoCancel(true)
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setContentIntent(pendingintent);
//
//            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(fees.this);
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            managerCompat.notify(1, builder.build());
//
////
                    //notification end

                }
            }

            private void getEphericalkey(String customerID) {



                StringRequest stringRequest=new StringRequest(Request.Method.POST,
                        "https://api.stripe.com/v1/ephemeral_keys",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject object=new JSONObject(response);
                                    Ephericalkey=object.getString("id");
                                    Toast.makeText(salary.this, Ephericalkey, Toast.LENGTH_SHORT).show();

                                    getClientSecret(customerID,Ephericalkey);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header=new HashMap<>();
                        header.put("Authorization","Bearer "+SECRET_KEY);
                        header.put("Stripe-Version","2022-11-15");

                        return header;
                    }

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params=new HashMap<>();
                        params.put("customer",customerID);
                        return params;
                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(salary.this);
                requestQueue.add(stringRequest);



            }

            private void getClientSecret(String customerID, String ephericalkey) {


                final String txt =text.getText().toString();

                StringRequest stringRequest=new StringRequest(Request.Method.POST,
                        "https://api.stripe.com/v1/payment_intents",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject object=new JSONObject(response);
                                    ClientSecret=object.getString("client_secret");

                                    Toast.makeText(salary.this, ClientSecret, Toast.LENGTH_SHORT).show();


                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header=new HashMap<>();
                        header.put("Authorization","Bearer "+SECRET_KEY);

                        return header;
                    }

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params=new HashMap<>();
                        params.put("customer",customerID);
                        params.put("amount",txt+"00");
                        params.put("currency","INR");
                        params.put("automatic_payment_methods[enabled]","true");

                        return params;
                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(salary.this);
                requestQueue.add(stringRequest);




            }

            private void Paymentflow() {

                paymentSheet.presentWithPaymentIntent(
                        ClientSecret,new PaymentSheet.Configuration("SPZONE"
                                ,new PaymentSheet.CustomerConfiguration(
                                customerID,
                                Ephericalkey
                        ))
                );}
        }

