package com.example.hp.goodease;

        import android.app.AlertDialog;
        import android.content.Context;
        import android.os.AsyncTask;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.URLEncoder;

/**
 * Created by ProgrammingKnowledge on 1/5/2016.
 */
public class BackgroundWorker extends AsyncTask<String,Void,String> {
   public Context context;
    AlertDialog alertDialog;
    public BackgroundWorker(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String update_url =  "http://goodease.000webhostapp.com/updated_location.php";
        if(type.equals("update")) {
            try {
                String Driver_ID = params[1];
                String Location = params[2];
                String State = params[3];
                URL url = new URL(update_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("driver_id","UTF-8")+"="+URLEncoder.encode(Driver_ID,"UTF-8")+"&"
                        +URLEncoder.encode("location","UTF-8")+"="+URLEncoder.encode(Location,"UTF-8")+"&"+
                        URLEncoder.encode( "state","UTF-8" )+"="+URLEncoder.encode( State,"UTF-8" );
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
       // alertDialog = new AlertDialog.Builder(context).create();
       // alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        TrackActivity.driver_id = result;
        Toast.makeText( context, result, Toast.LENGTH_SHORT ).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}




