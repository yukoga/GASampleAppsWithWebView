package com.example.yukoga.gasampleappswithwebview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GTMWebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GTMWebViewFragment extends Fragment {

    public GTMWebViewFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GTMWebViewFragment.
     */
    public static GTMWebViewFragment newInstance() {
        GTMWebViewFragment fragment = new GTMWebViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gtmweb_view, container, false);
        final WebView gtmWebView = (WebView) view.findViewById(R.id.gtmWebView);
        final Tracker tracker = AnalyticsApplication.getTracker();
        tracker.setScreenName("GTMWebView Fragment");
        gtmWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String scheme = url.split(":")[0];
                Boolean result = false;
                if (scheme.equals("tracking")) {
                    result = this.sendClickElements(url);
                } else {
                    if (url.contains("tracking=1")) result = this.sendClickLinks(url);
                }
                return result;
            }

            public boolean sendClickLinks(String url) {
                Log.i("GAWEBVIEW:LINK-TRACKING", url);
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("WebViewTracking")
                        .setAction("ClickLinks")
                        .setLabel(url)
                        .setValue(1000)
                        .build()
                );
                return false;
            }

            public boolean sendClickElements(String url) {
                Log.i("GAWEBVIEW:ELEM-TRACKING", url);
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("WebViewTracking")
                        .setAction("ClickElements")
                        .setLabel(url)
                        .setValue(1000)
                        .build()
                );
                return true;
            }
        });
        gtmWebView.getSettings().setJavaScriptEnabled(true);
        gtmWebView.loadUrl("http://storage.googleapis.com/ga-webview-bestpractice/webview-with-gtm.html");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
