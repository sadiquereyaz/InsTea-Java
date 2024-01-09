package in.instea.instea.SideNavMenu;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import in.instea.instea.R;
public class FragmentPlacement extends Fragment {

    LineChart lineChart;
    BarChart stackedChart;

    private List<String> xValuesLineChart;
    private List<String> xValuesStackBar;
    public FragmentPlacement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_placement, container, false);

        lineChart = view.findViewById(R.id.average_line_chart);
        stackedChart= view.findViewById(R.id.stack_chart);

        // Check the current theme
        int blackWhiteColor, color;
        blackWhiteColor = getResources().getColor(R.color.black);

        lineChart.getDescription().setEnabled(false);

        lineChart.setHighlightPerTapEnabled(true);
        // enable scaling and dragging
//        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.getAxisRight().setDrawLabels(false);
//        lineChart.setBackgroundColor(Color.BLACK);
        lineChart.animateX(2000);

        // Customize the legend
        Legend lineChartLegend = lineChart.getLegend();
        lineChartLegend.setForm(Legend.LegendForm.LINE); // Change the legend icons to squares (optional)
        lineChartLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        lineChartLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        lineChartLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lineChartLegend.setDrawInside(false);     // Draw the legend outside the chart area
        lineChartLegend.setTextColor(blackWhiteColor);

        xValuesLineChart = Arrays.asList(" ","2020", "2021", "2022", "2023"," ");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValuesLineChart));
        xAxis.setAxisLineWidth(1.4f);
        xAxis.setAxisLineColor(blackWhiteColor);
        xAxis.setLabelCount(5);
        xAxis.setTextColor(blackWhiteColor);
        xAxis.setGranularity(1f);       //enforces a minimum gap of 1 unit (for 1f) between labels
        xAxis.setDrawGridLines(false);


        YAxis yAxis = lineChart.getAxisLeft();
//        yAxis.setAxisMaximum(10f);        // maximum value on y-axis
        yAxis.setAxisMinimum(4f);         // starting value on y-axis
//        yAxis.setGranularity(2f);
        yAxis.setAxisLineWidth(1.5f);
        yAxis.setAxisLineColor(blackWhiteColor);
        yAxis.setTextColor(blackWhiteColor);
//        yAxis.setLabelCount(6);
        yAxis.setDrawGridLines(true);

        YAxis yAxisR = lineChart.getAxisRight();
        yAxisR.setDrawGridLines(true);
        yAxisR.setAxisMinimum(4f);

        List<Entry> entryComputer= new ArrayList<>();
        entryComputer.add(new Entry(1, 8.49f));
        entryComputer.add(new Entry(2, 8.72f));
        entryComputer.add(new Entry(3, 13.59f));
        entryComputer.add(new Entry(4, 11.1f));

        List<Entry> entryElectronics= new ArrayList<>();
        entryElectronics.add(new Entry(1, 5.5f));
        entryElectronics.add(new Entry(2, 5.8f));
        entryElectronics.add(new Entry(3, 7.5f));
        entryElectronics.add(new Entry(4, 8.8f));

        List<Entry> entryElectrical= new ArrayList<>();
        entryElectrical.add(new Entry(1, 6.8f));
        entryElectrical.add(new Entry(2, 5.3f));
        entryElectrical.add(new Entry(3, 6.51f));
        entryElectrical.add(new Entry(4, 8.71f));

        List<Entry> entryMech= new ArrayList<>();
        entryMech.add(new Entry(1, 6.52f));
        entryMech.add(new Entry(2, 4.49f));
        entryMech.add(new Entry(3, 6.15f));
        entryMech.add(new Entry(4, 8.07f));

        List<Entry> entryCivil= new ArrayList<>();
        entryCivil.add(new Entry(1, 6.8f));
        entryCivil.add(new Entry(2, 5.3f));
        entryCivil.add(new Entry(3, 5.42f));
        entryCivil.add(new Entry(4, 5.91f));

        //to fix origin problem
        List<Entry> origin= new ArrayList<>();
        origin.add(new Entry(0, 0));
        origin.add(new Entry(5, 0));

        LineDataSet lineDataSetAvgComp = new LineDataSet(entryComputer, "CSE");
//        lineDataSetAvgComp.setMode(LineDataSet.Mode.CUBIC_BEZIER); // Set drawing mode to cubic bezier (increase curvature)
        lineDataSetAvgComp.setColors(Color.RED); // Set line colors
        lineDataSetAvgComp.setDrawValues(true); // Show data values on the chart point
        lineDataSetAvgComp.setLineWidth(2.5f);
        lineDataSetAvgComp.setCircleRadius(5f);
        lineDataSetAvgComp.setValueTextColor(blackWhiteColor);
        lineDataSetAvgComp.setCircleHoleRadius(3f);
        lineDataSetAvgComp.setCircleHoleColor(blackWhiteColor);
        lineDataSetAvgComp.setCircleColor(Color.TRANSPARENT);
        lineDataSetAvgComp.setHighLightColor(blackWhiteColor);lineDataSetAvgComp.setValueTextSize(8f);

        LineDataSet lineDataSetAvgElectronics = new LineDataSet(entryElectronics, "ECE");
        lineDataSetAvgElectronics.setColors(Color.CYAN); // Set line colors
        lineDataSetAvgElectronics.setDrawValues(true); // Show data values on the chart point
        lineDataSetAvgElectronics.setLineWidth(2.5f);
        lineDataSetAvgElectronics.setCircleRadius(5f);
        lineDataSetAvgElectronics.setValueTextColor(blackWhiteColor);
        lineDataSetAvgElectronics.setCircleHoleRadius(3f);
        lineDataSetAvgElectronics.setCircleColor(Color.TRANSPARENT);
        lineDataSetAvgElectronics.setCircleHoleColor(blackWhiteColor);
        lineDataSetAvgElectronics.setHighLightColor(blackWhiteColor);
        lineDataSetAvgElectronics.setValueTextSize(8f);

        LineDataSet lineDataSetAvgElectrical = new LineDataSet(entryElectrical, "EE");
        lineDataSetAvgElectrical.setColors(Color.GREEN); // Set line colors
        lineDataSetAvgElectrical.setDrawValues(true); // Show data values on the chart point
        lineDataSetAvgElectrical.setLineWidth(2.5f);
        lineDataSetAvgElectrical.setCircleRadius(5f);
        lineDataSetAvgElectrical.setCircleHoleRadius(3f);
        lineDataSetAvgElectrical.setCircleColor(Color.TRANSPARENT);
        lineDataSetAvgElectrical.setCircleHoleColor(blackWhiteColor);
        lineDataSetAvgElectrical.setHighLightColor(blackWhiteColor);
        lineDataSetAvgElectrical.setValueTextColor(blackWhiteColor);
        lineDataSetAvgElectrical.setValueTextSize(8f);

        LineDataSet lineDataSetAvgMech = new LineDataSet(entryMech, "Mechanical");
        lineDataSetAvgMech.setColors(Color.MAGENTA); // Set line colors
        lineDataSetAvgMech.setDrawValues(true); // Show data values on the chart point
        lineDataSetAvgMech.setLineWidth(2.5f);
        lineDataSetAvgMech.setValueTextColor(blackWhiteColor);
        lineDataSetAvgMech.setCircleRadius(5f);
        lineDataSetAvgMech.setCircleHoleRadius(3f);
        lineDataSetAvgMech.setCircleHoleColor(blackWhiteColor);
        lineDataSetAvgMech.setCircleColor(Color.TRANSPARENT);
        lineDataSetAvgMech.setHighLightColor(blackWhiteColor);
        lineDataSetAvgMech.setValueTextSize(8f);

        LineDataSet lineDataSetAvgCivil = new LineDataSet(entryCivil, "Civil");
        lineDataSetAvgCivil.setColors(Color.GRAY); // Set line colors
        lineDataSetAvgCivil.setDrawValues(true); // Show data values on the chart
        lineDataSetAvgCivil.setLineWidth(2.5f);
        lineDataSetAvgCivil.setCircleRadius(4.5f);
        lineDataSetAvgCivil.setCircleHoleRadius(3f);
        lineDataSetAvgCivil.setCircleHoleColor(blackWhiteColor);
        lineDataSetAvgCivil.setCircleColor(Color.TRANSPARENT);
        lineDataSetAvgCivil.setHighLightColor(blackWhiteColor);
        lineDataSetAvgCivil.setValueTextColor(blackWhiteColor);
        lineDataSetAvgCivil.setValueTextSize(8f);

        LineDataSet lineDataSetAvgOrigin = new LineDataSet(origin, "");
        lineDataSetAvgOrigin.setCircleColor(Color.TRANSPARENT);
        lineDataSetAvgOrigin.setHighLightColor(Color.TRANSPARENT);
        lineDataSetAvgOrigin.setColors(Color.TRANSPARENT); // Set line colors
        lineDataSetAvgOrigin.setDrawCircles(false);
        lineDataSetAvgOrigin.setValueTextSize(0f);

        LineData lineData = new LineData(lineDataSetAvgOrigin,lineDataSetAvgComp,lineDataSetAvgElectronics,lineDataSetAvgElectrical,lineDataSetAvgMech, lineDataSetAvgCivil);
        lineChart.setData(lineData);
        lineChart.invalidate();

//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(lineDataSetAvgMech);
//
//        LineData data = new LineData(dataSets);
//        lineChart.setData(data);
//
//        // Customize the chart
//        lineChart.getDescription().setText("Average Package Over Years");
//        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                // Return the value (year) as a string without any conversion
//                return String.valueOf((int)value);
//            }
//        });
//
//        // Add a legend
//        Legend legend = lineChart.getLegend();
//        legend.setForm(Legend.LegendForm.LINE);
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setDrawInside(false);
//        lineChart.invalidate();

        BarDataSet barDataSet = new BarDataSet(dataValues1(),"");
        int barRed = ContextCompat.getColor(requireContext(), R.color.barRed);
        int barBlue = ContextCompat.getColor(requireContext(), R.color.barBlue);
        int barGreen = ContextCompat.getColor(requireContext(), R.color.barGreen);

        int barColor[]={barBlue, barRed, barGreen};

        barDataSet.setColors(barColor);
        // Set labels for each data set
        barDataSet.setStackLabels(new String[]{"Placed", "Offer", "Applied"});

        BarData barData = new BarData(barDataSet);
        stackedChart.getDescription().setEnabled(false);
        stackedChart.getAxisRight().setEnabled(true);
        stackedChart.getAxisLeft().setEnabled(true);
        stackedChart.getAxisRight().setTextColor(blackWhiteColor);
        stackedChart.getAxisRight().setAxisLineColor(blackWhiteColor);
        stackedChart.getAxisLeft().setAxisLineColor(blackWhiteColor);
        stackedChart.getAxisRight().setGridColor(blackWhiteColor);
        stackedChart.animateY(2000);
        stackedChart.getAxisRight().setDrawLabels(false);
        stackedChart.getAxisLeft().setDrawLabels(false);

        barDataSet.setBarBorderColor(blackWhiteColor);
        barDataSet.setBarBorderWidth(0.5f);
        barDataSet.setValueTextSize(8f);
        barDataSet.setValueTextColor(blackWhiteColor);

        stackedChart.setData(barData);

        xValuesStackBar = Arrays.asList(" ","CSE", "ECE", "ME", "EE","CE"," ");

        XAxis xAxisBar = stackedChart.getXAxis();
        xAxisBar.setValueFormatter(new IndexAxisValueFormatter(xValuesStackBar));
        xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBar.setAxisLineWidth(1.4f);
        xAxisBar.setAxisLineColor(blackWhiteColor);
        xAxisBar.setTextColor(blackWhiteColor);
//        yAxis.setLabelCount(6);
        xAxisBar.setDrawGridLines(false);

        YAxis yAxisBarLeft = stackedChart.getAxisLeft();
        yAxisBarLeft.setAxisLineWidth(1f);
        yAxisBarLeft.setAxisMinimum(0f);
//        yAxisBarLeft.setAxisLineColor(Color.BLACK);
//        yAxis.setLabelCount(6);
//

        YAxis yAxisBarRight = stackedChart.getAxisRight();
        yAxisBarRight.setAxisLineWidth(1f);
        yAxisBarRight.setAxisMinimum(0f);


        // Customize the legend
        Legend legend = stackedChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE); // Change the legend icons to squares (optional)
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setTextColor(blackWhiteColor);
        legend.setDrawInside(true);     // Draw the legend inside the chart area


        return view;
    }

    private ArrayList<BarEntry> dataValues1() {

        ArrayList<BarEntry> dataValues1 = new ArrayList<BarEntry>();
        dataValues1.add(new BarEntry(0, new float[]{0 , 0 ,0}));
        dataValues1.add(new BarEntry(1, new float[]{56, 65 ,79}));
        dataValues1.add(new BarEntry(2, new float[]{43, 46 ,82}));
        dataValues1.add(new BarEntry(3, new float[]{33, 34,76}));
        dataValues1.add(new BarEntry(4, new float[]{32,32,85}));
        dataValues1.add(new BarEntry(5, new float[]{34,35,80}));
        dataValues1.add(new BarEntry(6, new float[]{0 , 0f ,0}));
        return dataValues1;
    }
}