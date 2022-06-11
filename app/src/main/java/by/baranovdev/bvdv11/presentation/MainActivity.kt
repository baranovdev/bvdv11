package by.baranovdev.bvdv11.presentation

import android.graphics.Color
import android.graphics.Interpolator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.baranovdev.bvdv11.R
import by.baranovdev.bvdv11.model.WeatherForecast
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.squareup.picasso.Picasso
import java.util.*


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.srl)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        viewModel.forTomorrow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > 18
        viewModel.getData()
        viewModel.weatherForecastLiveData.observe(this){
            findViewById<TextView>(R.id.tv_title).text = viewModel.title
            Picasso.with(this).load("https:${it?.currentDay?.imageUrl}")
                .into(
                    findViewById<ImageView>(R.id.text)
                )
            findViewById<TextView>(R.id.tv_temperature).text = viewModel.setTemperatureText(it?.currentDay?.mintemp_c,it?.currentDay?.maxtemp_c)
            refreshLayout.isRefreshing = false
            if(it!=null)updateChart(it!!)
        }
        viewModel.isLoadingLiveData.observe(this){
            if(it)progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }

        refreshLayout.setOnRefreshListener {
            viewModel.getData()
        }

    }

    fun updateChart(forecast : WeatherForecast){
        val chart = findViewById<LineChart>(R.id.chart)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.setBackgroundColor(Color.WHITE)
        val values: ArrayList<Entry> = ArrayList()
        for (i in forecast.listHours) {
            values.add(
                Entry(
                    "${i.time[11]}${i.time[12]}".toFloat(),
                    i.temp_c.toFloat()
                )
            )
        }
        chart.animateX(500)
        chart.xAxis.valueFormatter = formatter
        val set = LineDataSet(values, "DataSet 1")
        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(set)
        val data = LineData(dataSets)
        chart.data = data
    }
}

object formatter : ValueFormatter(), IAxisValueFormatter{
    override fun getFormattedValue(value: Float): String {
        return if(value.toInt()<10) "0${value.toInt()}:00"
        else "${value.toInt()}:00"
    }
}