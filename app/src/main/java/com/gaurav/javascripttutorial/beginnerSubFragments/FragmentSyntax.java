package com.gaurav.javascripttutorial.beginnerSubFragments;


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

public class FragmentSyntax extends Fragment {

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_syntax,container,false);

        webView = (WebView) view.findViewById(R.id.webview_syntax);
        webView.loadUrl("file:///android_asset/syntax.html");
        webView.getSettings().setJavaScriptEnabled(true);
        return view;
    }
}
