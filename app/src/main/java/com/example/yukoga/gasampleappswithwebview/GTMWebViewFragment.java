package com.example.yukoga.gasampleappswithwebview;

import android.content.Context;
import android.net.Uri;
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
 * Activities that contain this fragment must implement the
 * {@link GTMWebViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GTMWebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GTMWebViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public GTMWebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GTMWebViewFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static GTMWebViewFragment newInstance(String param1, String param2) {
    public static GTMWebViewFragment newInstance() {
        GTMWebViewFragment fragment = new GTMWebViewFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
