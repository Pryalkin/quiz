package com.muzika.app_quiz;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NameDialog extends DialogFragment {

    private EditText et;
    private RequestQueue requestQueue;
    String hello;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View root = inflater.inflate(R.layout.name_dialog, null);
        et = root.findViewById(R.id.name_dialog_in);
        Dialog d = builder
                .setView(root)
                .setTitle("Имя игрока")
                .setPositiveButton("OK", (dialog, which) -> {
//                    String url = "http://10.0.2.2:8080/authorization/username";
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                            response -> { }
//                            ,
//                            error -> {}
//                    )
//                    {
//                      @Override
//                        protected Map<String, String> getParams() throws AuthFailureError{
//                          Map<String, String> params = new HashMap<>();
//                          params.put("username", et.getText().toString());
//                          return params;
//                      }
//                    };
                    Result.result = 0;
                    Result.username = et.getText().toString();
                    Intent intent = new Intent(getContext(), QuestionsActivity.class);
                    startActivity(intent);
//                    requestQueue = Volley.newRequestQueue(getContext());
//                    requestQueue.add(stringRequest);
                    Toast.makeText(getActivity(), "Hello " + Result.username, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .create();
//        LOSUtils.dialog(d);
        return d;
    }

}
