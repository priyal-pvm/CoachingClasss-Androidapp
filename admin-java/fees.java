package com.admin.spzone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.stripe.android.paymentsheet.PaymentSheet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class fees extends AppCompatActivity {

            Button button;
            EditText text;

            //    String SECRET_KEY="sk_test_51MoXFlSIGSPZ7RJFgISBfTF4xpOsSwaycLA5n494yGQyRWHGU5pLFLqUTOqbGQXYyAF6oKt5fNSpUNopMsUsbL4I00OlZ3Zn6V";
//    String PUBLISH_KEY="pk_test_51MoXFlSIGSPZ7RJFA26dSitILLd12CNGOkaNeiNIGcgrhK4x3LCuhnVxv3g9aeJSELOeqkvUjwLFNpIYQFh5qji100Q4Lq1u6j";
            PaymentSheet paymentSheet;
            String currency = "INR";
            String acceptPartial = "true";
            String referenceID;
            String firstMinPartialAmount = "100";
            String expireBy = "1691097357";
            String description = "Test payment";
            String name = "Independent Work App";

            //    String amount;
            String sms = "true";
            String notifyEmail = "" +
                    "true";
            String reminderEnable = "true";

//                Button button;
//                EditText text;
//
//                String SECRET_KEY = "sk_test_51MoXFlSIGSPZ7RJFgISBfTF4xpOsSwaycLA5n494yGQyRWHGU5pLFLqUTOqbGQXYyAF6oKt5fNSpUNopMsUsbL4I00OlZ3Zn6V";
//                String PUBLISH_KEY = "pk_test_51MoXFlSIGSPZ7RJFA26dSitILLd12CNGOkaNeiNIGcgrhK4x3LCuhnVxv3g9aeJSELOeqkvUjwLFNpIYQFh5qji100Q4Lq1u6j";
//                PaymentSheet paymentSheet;

                //    String amounttext
                String customerID;
                String Ephericalkey;
                String ClientSecret;
    @SuppressLint("MissingInflatedId")

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_fees);


                    text=findViewById(R.id.amount);
                        final String amo=text.getText().toString();

                button=findViewById(R.id.pay);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        referenceID = UUID.randomUUID().toString();
//                        text=findViewById(R.id.amount);
//                        final String amo=text.getText().toString();
//
//                        button=findViewById(R.id.pay);
//
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                referenceID = UUID.randomUUID().toString();
                                if (TextUtils.isEmpty(amo)) {
                                    Toast.makeText(fees.this, "Enter Amount", Toast.LENGTH_SHORT).show();

                                }
//
//                                else {
//                                    paymentRequest();
//                                }
//                            });

                            paymentRequest();
                    }
                });


            }

            private void paymentRequest()
            {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.razorpay.com/v1/payment_links",
                        new Response.Listener<String>() {




                            @Override
                            public void onResponse(String response) {
                                // Handle the response
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                JsonParser jp = new JsonParser();
                                JsonElement je = jp.parse(response);
                                String prettyJsonString = gson.toJson(je);
                                Log.e("anyText",prettyJsonString);
                                try {
//                            dialog.dismiss();
//                            i=2;
                                    JSONObject jsonObject = new JSONObject(response);
                                    Intent pay=new Intent(fees.this,webview.class);
                                    pay.putExtra("paymentLink",jsonObject.getString("short_url"));
                                    startActivity(pay);
                                    String paymentId=jsonObject.getString("id");
//                            createEvent(id);
//                            sp.setPaymentId(paymentId);
                                    Log.e("anyText",paymentId);
                                } catch (JSONException e) {
//                            dialog.dismiss();
                                    Toast.makeText(fees.this, "Something Went Wrong,Try After Some time",Toast.LENGTH_SHORT).show();
                                    throw new RuntimeException(e);
                                }
//                        Toast.makeText(CreateEvent.this, "Payment Request Sent", Toast.LENGTH_SHORT).show();
//                        btn_pay_status.setVisibility(View.VISIBLE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle the error
//                        dialog.dismiss();
                                Log.e("anyText",""+error);
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Basic cnpwX3Rlc3RfUHZNNEd4SzlNWWxDVWM6V3pzT1RSQVU0bDNvQUExQ1M3amxWUzVF");
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }


                    @Override
                    public byte[] getBody() throws AuthFailureError {

                        final String txt = text.getText().toString();

                        String requestBody = "{\n" +
                                "  \"amount\": " + (Integer.parseInt(txt)*100) + ",\n" +
//                        "  \"amount\": " + (Integer.parseInt(""+1000)*100) + ",\n" +
                                "  \"currency\": \"" + currency + "\",\n" +
                                "  \"accept_partial\": " + acceptPartial + ",\n" +
                                "  \"first_min_partial_amount\": " + firstMinPartialAmount + ",\n" +
                                "  \"expire_by\": " + expireBy + ",\n" +
                                "  \"reference_id\": \"" + referenceID + "\",\n" +
                                "  \"description\": \"" + description + "\",\n" +
                                "  \"customer\": {\n" +
                                "    \"name\": \"" + name + "\",\n" +
                                "    \"contact\": \"" + "+917572876141" + "\",\n" +
                                "    \"email\": \"" + "1074.pritesh@gmail.com" + "\"\n" +
                                "  },\n" +
                                "  \"notify\": {\n" +
                                "    \"sms\": " + sms + ",\n" +
                                "    \"email\": " + notifyEmail + "\n" +
                                "  },\n" +
                                "  \"reminder_enable\": " + reminderEnable + ",\n" +
                                "  \"notes\": {\n" +
                                "    \"policy_name\": \"" + "SPZONE" + "\"\n" +
                                "  }\n" +
                                "}";
                        return requestBody.getBytes();
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(stringRequest);
            }

        }
