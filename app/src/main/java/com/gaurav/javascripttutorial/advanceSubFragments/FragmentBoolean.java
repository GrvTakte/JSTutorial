package com.gaurav.javascripttutorial.advanceSubFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.gaurav.javascripttutorial.R;

/**
 * Created by gaurav on 21/09/17.
 */

public class FragmentBoolean extends Fragment {

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_boolean,container,false);
        webView = (WebView) view.findViewById(R.id.webview_boolean);
        webView.loadUrl("file:///android_asset/boolean.html");
        webView.getSettings().setJavaScriptEnabled(true);
        return view;
    }
}
