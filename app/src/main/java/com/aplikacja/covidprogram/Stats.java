package com.aplikacja.covidprogram;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Stats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Stats extends Fragment {


    public TextView t1, t2, t3, t4, t5;
    public TextView gf;
    public ArrayList<String> wojki = new ArrayList<>();
    public ArrayList<String> liczb = new ArrayList<>();
    public ArrayList<String> liczb10 = new ArrayList<>();
    public ArrayList<String> liczbSm = new ArrayList<>();
    public ArrayList<String> liczbZak = new ArrayList<>();

    String test1 = "", test2 = "", test3 = "", test4 = "", test5 = "", test11 = "";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Stats() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Stats.
     */
    // TODO: Rename and change types and number of parameters
    public static Stats newInstance(String param1, String param2) {
        Stats fragment = new Stats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View v = inflater.inflate(R.layout.fragment_stats, container, false);

        //region zmienne
        t1 = (TextView) v.findViewById(R.id.q1);
        t2 = (TextView) v.findViewById(R.id.q2);
        t3 = (TextView) v.findViewById(R.id.q3);
        t4 = (TextView) v.findViewById(R.id.q4);
        t5 = (TextView) v.findViewById(R.id.q5);
        //endregion
        new doinbackground().execute();


        return v;
    }

    public void setvalue() {
        t1.setText(wojki.get(15));
        t2.setText(liczb.get(15));
        t3.setText(liczb10.get(15));
        t4.setText(liczbSm.get(15));
        t5.setText(liczbZak.get(15));

    }

    class doinbackground extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            setvalue();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document docum = Jsoup.connect("https://koronawirusunas.pl/")
                        .timeout(30 * 1000)
                        .get();
                Document docum2 = Jsoup.connect("https://www.medicover.pl/koronawirus/mapa/")
                        .timeout(6000)
                        .get();
                Elements element = docum.select("table.table.table-sm.table-borderless tbody");
                Elements element2 = docum2.select("tbody");

                for (Element e : element.select("tr")) {
                    for (int l = 0; l < 1; l++) {
                        test11 = e.select("td a").text();
                        for (Element o : element2.select("tr")) {
                            boolean jest = false;
                            for (Element x : o.select("td")) {
                                String data3 = x.select("td").text();
                                if (test11.equals(data3)) {
                                    jest = true;
                                }
                            }
                            if (jest) {
                                int i = 0;
                                for (Element r : o.select("td")) {
                                    if (i == 0) {
                                        test1 += r.select("td").text();
                                        test1 += "\n";
                                        test1 += "\n";
                                        wojki.add(test1);
                                    }
                                    if (i == 1) {
                                        test2 += r.select("td").text();
                                        test2 += "\n";
                                        test2 += "\n";
                                        liczb.add(test2);
                                    }
                                    if (i == 2) {
                                        test3 += r.select("td").text();
                                        test3 += "\n";
                                        test3 += "\n";
                                        liczb10.add(test3);
                                    }
                                    if (i == 3) {
                                        test4 += r.select("td").text();
                                        test4 += "\n";
                                        test4 += "\n";
                                        liczbSm.add(test4);
                                    }
                                    i++;
                                }

                            }
                        }
                        test5 += e.select("td div.progress-bar.bg-danger").text();
                        test5 += "\n";
                        test5 += "\n";
                        liczbZak.add(test5);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}