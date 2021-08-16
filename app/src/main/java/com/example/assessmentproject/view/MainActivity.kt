package com.example.assessmentproject.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.assessmentproject.R
import com.example.assessmentproject.adapters.TemperAdapter
import com.example.assessmentproject.utility.Config
import com.example.assessmentproject.utility.Globals
import com.example.assessmentproject.utility.NetworkStateReceiver
import com.example.assessmentproject.utility.Util
import com.example.assessmentproject.viewmodel.TemperViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() , NetworkStateReceiver.NetworkStateReceiverListener{
    lateinit var viewModel:  TemperViewModel
    lateinit var temperListAdapter: TemperAdapter
    private var networkStateReceiver: NetworkStateReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        clicks()
        initRecyclerView()
        pullToRefresh()
    }

    private fun getCurrentDate(): String{
        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val now: LocalDateTime = LocalDateTime.now()
        return dtf.format(now)
    }

    override fun onStart() {
        super.onStart()
        setupNetworkStateReceiver()
    }

    override fun onStop() {
        super.onStop()
        removeNetworkStateReceiver()
    }

    private fun removeNetworkStateReceiver(){
        networkStateReceiver?.removeListener(this)
        unregisterReceiver(networkStateReceiver)
    }

    private fun setupNetworkStateReceiver(){
        networkStateReceiver = NetworkStateReceiver()
        networkStateReceiver?.addListener(this)
        val i = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkStateReceiver, i)
    }

    private fun clicks(){
        temperCardViewFilter.setOnClickListener {
            Util.showToastMessage(this, getString(R.string.not_implemented))
        }

        txtLogin.setOnClickListener {
            startActivity(
                Intent(this, LogRegActivity::class.java)
                    .putExtra(Config.ISLOGIN, true)
            )
        }

        txtRegistration.setOnClickListener {
            startActivity(
                Intent(this, LogRegActivity::class.java)
                    .putExtra(Config.ISLOGIN, false)
            )
        }

        btnOpenMapView.setOnClickListener {
            val g = application as Globals
            g.setData(temperListAdapter.temperListData)
            /*val dataBundle = Bundle()
            dataBundle.putString(Config.MAPDATE, Gson().toJson(temperListAdapter.temperListData))*/
            startActivity(
                Intent(this, MapViewActivity::class.java)
            )
        }
    }

    private fun pullToRefresh(){
        sweepTemperList.setOnRefreshListener {
            loadAPIData(getCurrentDate())
        }
    }
    private fun initRecyclerView(){
        temperRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration  = DividerItemDecoration(
                applicationContext,
                StaggeredGridLayoutManager.VERTICAL
            )
            addItemDecoration(decoration)
            temperListAdapter = TemperAdapter()
            adapter =temperListAdapter
        }
    }
    private fun loadAPIData(input: String) {
        viewModel = ViewModelProvider(this).get(TemperViewModel::class.java)
        viewModel.getTemperList().observe(this, {
            temperBar.visibility = View.GONE
            temperRecyclerView.visibility = View.VISIBLE
            sweepTemperList.isRefreshing = false
            if (it != null) {
                temperListAdapter.temperListData = it.data
                temperListAdapter.notifyDataSetChanged()
            } else {
                Util.showToastMessage(this, getString(R.string.error_in_fetching_data))
            }
        })
        viewModel.makeApiCall(input)
    }

    override fun networkAvailable() {
        loadAPIData(getCurrentDate())
    }

    override fun networkUnavailable() {
        Util.showToastMessage(this, getString(R.string.network_not_available))
    }


}
