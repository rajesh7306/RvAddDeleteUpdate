package com.example.rvadddeleteupdate;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Model> models = new ArrayList<Model>();
    RecyclerView rvTechSolPoint;
    RvAdapter rvAdapter;
    TextView tvAdd, tvUpdate;
    EditText etEnterName;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTechSolPoint = findViewById(R.id.rv_list_item);
        tvAdd = findViewById(R.id.tv_add);
        etEnterName = findViewById(R.id.et_enter_name);
        tvUpdate = findViewById(R.id.tv_update);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvTechSolPoint.setLayoutManager(layoutManager);
        rvAdapter = new RvAdapter(getApplicationContext(), models,
                new RvAdapter.Onclick() {
                    @Override
                    public void onEvent(Model model, int pos) {
                        position = pos;
                        tvUpdate.setVisibility(View.VISIBLE);
                        etEnterName.setText(model.getName());
                    }
                });
        rvTechSolPoint.setAdapter(rvAdapter);

        tvAdd.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add: {
                insertMethod(String.valueOf(etEnterName.getText()));
            }
            break;
            case R.id.tv_update: {
                models.get(position).setName(etEnterName.getText().toString());
                rvAdapter.notifyDataSetChanged();
                tvUpdate.setVisibility(View.GONE);
            }
            break;
        }
    }

    private void insertMethod(String name) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            Model model = gson.fromJson(String.valueOf(jsonObject), Model.class);
            models.add(model);
            rvAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
