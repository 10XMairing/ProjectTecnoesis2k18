package com.example.tenx.test272.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tenx.test272.Activity.Adapters.WhatsHotAdapter;
import com.example.tenx.test272.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class FragmentWhatsHot extends Fragment{

    RecyclerView recyclerView;
    WhatsHotAdapter adapter;
    FloatingActionButton fab;
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        fab = view.findViewById(R.id.scanner_bar);
        recyclerView = view.findViewById(R.id.recyclerView_whatshot);
        adapter = new WhatsHotAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    startQRScanner();

            }
        });
        return view;
    }
    private void startQRScanner() {
        new IntentIntegrator(this.getActivity()).forSupportFragment(this).initiateScan();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result =   IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(),    "Cancelled",Toast.LENGTH_LONG).show();
            } else {
                openWebPage(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    }
