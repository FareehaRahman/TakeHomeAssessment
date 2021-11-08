package com.example.realfetchproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> name = new ArrayList<>();          //Stores the Names from file
    ArrayList<String> groupOfIds = new ArrayList<>();    //Stores the Ids from File



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            //Take the contents of the file and put them in a array
            JSONArray jsonArray = new JSONArray(getJson());
            for (int i = 0; i < jsonArray.length(); i++) {
                //After looping thorough the file array then, find each row which is an object of the array
                JSONObject row = jsonArray.getJSONObject(i);
                String list = row.getString("listId");     //Stores the value of the each list id in a string
                String name12 = row.getString("name");     //Stores the value of the each name in a string
                //Checks to see if the name is not null and not empty, if not then add them into there own seperate arrays
                if((name12 != "null") && (!name12.isEmpty())){
                    groupOfIds.add(list);
                    name.add(name12);
                }
                //Sorts the names and ids, for ids I was able to use the sort function but for the names, I had to compare each number in string
                //So I made a function doing that. More explained on line 82.
                Collections.sort(groupOfIds);
                sortNames(name);
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        //This creates an instance of the class Adapter, and sets up the recycler view so that
        //It could be set up using the adapter tells it to.
        //The output groups the ids and then prints out the ids first and then name underneath it.
        Adapter setUp = new Adapter(name, groupOfIds, MainActivity.this);
        recyclerView.setAdapter(setUp);

    }
    //This Function opens the json file
    private String getJson() throws IOException {
        String json = null;

        InputStream inputStream = getAssets().open("hiring.json");
        int sizeOfFile = inputStream.available();
        byte[] bufferData = new byte[sizeOfFile];
        inputStream.read(bufferData);
        inputStream.close();
        json = new String(bufferData, "UTF-8");

        return json;
    }

    //This Function gets the names from the array and then compares the numbers by using the sort function
    //Using Sort function, it compares the first string in the array and the one after it and puts it in order
    //Since the arrayList is an list with strings that includes numbers, and to focus only on the numbers, I
    //parsed the integer and then from their, it checks to see if the value of difference is greaater or lesser
    //If lesser then you know it's smaller value and is going to be ahead of the value that is greater.
    private void sortNames(ArrayList<String> name) throws JSONException {
        Collections.sort(name, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
    }
}
