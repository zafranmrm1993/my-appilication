package com.example.assessmentproject.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assessmentproject.R
import com.example.assessmentproject.model.Data
import com.example.assessmentproject.utility.Util
import kotlinx.android.synthetic.main.item_view.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TemperAdapter : RecyclerView.Adapter<TemperAdapter.MyViewHolder>() {
    var temperListData = ArrayList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(temperListData[position])
    }
    override fun getItemCount(): Int {
        return temperListData.size
    }
    class   MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val txEarningsPerHour = view.txtItemEarningsPerHour
        private val tvCategory = view.txtItemCategory
        private val tvJobTitle = view.txtItemJobTitle
        private val tvTimeInterval = view.txtItemTimeInterval
        private val imgFeaturesImage = view.imgFeaturesImages
        @SuppressLint("SetTextI18n")
        fun bind(data: Data){
            txEarningsPerHour.text = getCurrencySymbol(data.earnings_per_hour.currency)  +" "+data.earnings_per_hour.amount
            data.earnings_per_hour.amount.toString()
            tvCategory.text = data.job.category.name
            tvJobTitle.text = data.job.title
            tvTimeInterval.text = Util.getFormatDate(data.starts_at )+" - "+ Util.getFormatDate(data.ends_at)
            val url  = data.job.project.client.links.hero_image
            Glide.with(imgFeaturesImage)
                .load(url)
                .into(imgFeaturesImage)
        }
        private fun getCurrencySymbol(symbol:String):String{
            return when(symbol) {
                "EUR" -> {"€"}  else -> { "DOLLAR" }
            }
        }
    }
}