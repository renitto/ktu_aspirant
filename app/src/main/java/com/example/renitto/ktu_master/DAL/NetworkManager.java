package com.example.renitto.ktu_master.DAL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import com.example.renitto.ktu_master.Application;
import com.example.renitto.ktu_master.Models.FacebookFeed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;

//import com.singnet.peppercastle.Model.MODELReview;

/**
 * Created by SAYONI on 08-12-2015.
 */


public class NetworkManager {

    String userid;


    public static final int GET_FACEBOOK_FEEDS = 1;



    public interface onServerDataRequestListener {
        public void showData(Object data, int whatToShow);

        public void onErrorResponse(String error);
    }
    public static void SendDataToServer(final onServerDataRequestListener listener, final int whatToSend, Context context, final Object params) {
        JsonObjectRequest request=null;


        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);

        final Gson gson = new GsonHelper().getGson();





        try {
            String object=  new JSONObject(gson.toJson(params)).toString();
            request = new JsonObjectRequest(Request.Method.POST, getUrl(whatToSend,null), new JSONObject(gson.toJson(params)), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    switch (whatToSend) {

                    }

                }

            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    // Error handling
                    System.out.println("Something went wrong!");
                    error.printStackTrace();
                    listener.onErrorResponse(error.getLocalizedMessage());

                    pDialog.hide();

                }
            }

            );
            pDialog.hide();
        }
        catch (Exception ex){}




      Application.getInstance().addToRequestQueue(request);
    }


    public static void GetDataFromServer(final onServerDataRequestListener listener, final int whatToFetch, final Context context, String[] params) {

//if(new ConnectionDetector(context).isConnectingToInternet()) {

    final ProgressDialog pDialog = new ProgressDialog(context);


            pDialog.setMessage("Loading...");
            pDialog.show();
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setCancelable(false);




    final Gson gson = new GsonHelper().getGson();
//       final Gson gson = new GsonBuilder()
//                .setDateFormat("dd-MM-yyyy")
//                .create();
//               .registerTypeAdapter(Date.class, new NetDateTimeAdapter())
//                .setDateFormat("dd-MM-yyyy")
//                .create();
    StringRequest stringRequest = new StringRequest(Request.Method.GET, getUrl(whatToFetch, params),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    switch (whatToFetch) {



                        case GET_FACEBOOK_FEEDS:
                            listener.showData(gson.fromJson(response, FacebookFeed.class), GET_FACEBOOK_FEEDS);
                            break;




                    }


                    pDialog.hide();
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            // Error handling
            System.out.println("Something went wrong!");
            error.printStackTrace();
            listener.onErrorResponse(error.getLocalizedMessage());

            pDialog.hide();

        }
    });
    stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    Application.getInstance().addToRequestQueue(stringRequest);


//    else
//    Toast.makeText(context,"Please check your internet connection and try again !",Toast.LENGTH_LONG).show();

    }

    public static void SendToServer(String url, final Activity activity, final int whatToUpdate) {

    }

    private static String getUrl(int whatToDo, String[] params) {



        String base_url = "https://graph.facebook.com/v2.7/";


        String ktu_paeId = "728170017312651" ;
        String moto_paeId = "222958027895256" ;
        String ktu_official_id = "1510885542517120";





        switch (whatToDo) {



            case GET_FACEBOOK_FEEDS:

                String FbAccesToken= "EAAB9RlOTF4YBABaCQrh74Db1sKhuE2YXgppw9g33mqa46i6Pysku55D391fmYWWFv9wA9hl1INQX9n9crwh2VfnZCcfg8OeywMLJjae8wu5ylh9dmqvvQMhIpBWZBVx9bWLWAMJXSkGjv4AkH3MoXpTv05zhIGTpHYpJa3PZCweqyj4iCTcRFih7DfrKMYMgeBoKRDVUSS4jIZCQmn82";

                return base_url +ktu_official_id + "?fields=posts.limit(100)%7Battachments%2Ccreated_time%7D&access_token=" + Application.getInstance().FbAccessToken ;





            default:
                return null;
        }
    }

    public static class GsonHelper {
        public Gson getGson() {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new DotNetDateDeserializer());
            builder.registerTypeAdapter(Date.class, new DotNetDateSerializer());
            return builder.create();
        }

        public class DotNetDateDeserializer implements JsonDeserializer<Date> {
            @Override
            public Date deserialize(JsonElement json, Type typfOfT, JsonDeserializationContext context) {
                try {
                    String dateStr = json.getAsString();
                    if (dateStr != null) dateStr = dateStr.replace("/Date(", "");
                    if (dateStr != null) dateStr = dateStr.replace("+0530)/", "");
                    if (dateStr != null) dateStr = dateStr.replace(")/", "");
                    long time = Long.parseLong(dateStr);
                    return new Date(time);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }

            }
        }

        public class DotNetDateSerializer implements JsonSerializer<Date> {
            @Override
            public JsonElement serialize(Date date, Type typfOfT, JsonSerializationContext context) {
                if (date == null)
                    return null;

                String dateStr = "/Date(" + date.getTime() + ")/";
                return new JsonPrimitive(dateStr);
            }
        }

    }


}
