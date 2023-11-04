package com.example.apprscovid_19.hospital

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apprscovid_19.R

class HospitalAdapter(private val hospitals: List<Hospital>, private val onItemClick: (Hospital) -> Unit) :
    RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daftar_rumah_sakit, parent, false)
        return HospitalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val hospital = hospitals[position]
        holder.bind(hospital)

    }

    override fun getItemCount(): Int {
        return hospitals.size
    }

    inner class HospitalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.textViewName)
        private val tvAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        private val tvRegion: TextView = itemView.findViewById(R.id.textViewRegion)
        private val tvPhone: TextView = itemView.findViewById(R.id.textViewPhone)
        private val tvProvince: TextView = itemView.findViewById(R.id.textViewProvince)
        private val ivThumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)
        private val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)

        fun bind(hospital: Hospital) {
            tvName.text = hospital.name
            tvAddress.text = hospital.address
            tvRegion.text = hospital.region
            tvPhone.text = hospital.phone
            tvProvince.text = hospital.province

            val colorResId = when (adapterPosition % 3) {
                0 -> R.drawable.gradient_background
                1 -> R.drawable.gradient_background2
                else -> R.drawable.gradient_background3
            }
            linearLayout.setBackgroundResource(colorResId)

            tvProvince.setOnClickListener {
                onItemClick(hospital)
            }
        }
    }
}