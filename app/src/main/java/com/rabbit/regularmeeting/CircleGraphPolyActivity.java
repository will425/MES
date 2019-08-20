package com.rabbit.regularmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CircleGraphPolyActivity extends AppCompatActivity {

    private PieChart pieChart;

    private static String TAG = "phptest_MainActivity";

    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID = "date";
    private static final String TAG_NAME = "product_cnt";
    private static final String TAG_ADDRESS ="defective_cnt";
    private static float product_cnt_f;
    private static float defective_cnt_f;

    //private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_graph_poly);

        mArrayList = new ArrayList<>();

        CircleGraphPolyActivity.GetData task = new CircleGraphPolyActivity.GetData();
        task.execute("http://211.170.81.147/JcastMesPortable/DefectiveRate.php");
    }

    private class GetData extends AsyncTask<String, Void, String>
    {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(CircleGraphPolyActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params)
        {
            String serverURL = params[0];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else {
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();
            }
            catch (Exception e)
            {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
    }

    private void showResult()
    {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String date = item.getString(TAG_ID);
                String product_cnt = item.getString(TAG_NAME);
                String defective_cnt = item.getString(TAG_ADDRESS);

                product_cnt_f = Float.parseFloat(product_cnt);
                defective_cnt_f = Float.parseFloat(defective_cnt);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, date);
                hashMap.put(TAG_NAME, product_cnt);
                hashMap.put(TAG_ADDRESS, defective_cnt);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    CircleGraphPolyActivity.this, mArrayList, R.layout.item_list,
                    new String[]{TAG_ID,TAG_NAME, TAG_ADDRESS},
                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
            );
            //mlistView.setAdapter(adapter);

            pieChart = (PieChart) findViewById(R.id.piepolychart);

            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setExtraOffsets(5,10,5,5);

            pieChart.setDragDecelerationFrictionCoef(0.95f);

            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);

            pieChart.setTransparentCircleRadius(61f);

            ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

            yValues.add(new PieEntry(product_cnt_f,"생산량"));
            yValues.add(new PieEntry(defective_cnt_f,"불량수량"));
            //yValues.add(new PieEntry(14f,"UK"));
            //yValues.add(new PieEntry(35f,"India"));
            //yValues.add(new PieEntry(40f,"Russia"));
            //yValues.add(new PieEntry(40f,"Korea"));

            Description description = new Description();
            description.setText("불량률 현황"); //라벨
            description.setTextSize(15);
            pieChart.setDescription(description);

            pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

            PieDataSet dataSet = new PieDataSet(yValues,"Countries");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

            PieData data = new PieData((dataSet));
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);

            pieChart.setData(data);

        }
        catch (JSONException e)
        {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
