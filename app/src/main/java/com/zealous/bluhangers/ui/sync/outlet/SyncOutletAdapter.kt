package com.zealous.bluhangers.ui.sync.outlet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.zealous.bluhangers.R
import com.zealous.bluhangers.data.model.Outlet
import kotlinx.android.synthetic.main.item_outlet.view.*


class SyncOutletAdapter : RecyclerView.Adapter<SyncOutletAdapter.SyncOutletViewHolder>() {

    private var listOutlet = mutableListOf<Outlet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyncOutletViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_outlet, parent, false)
        return SyncOutletViewHolder(view)
    }

    override fun getItemCount(): Int = this.listOutlet.size

    inner class SyncOutletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(outlet: Outlet){
            with(itemView){
                tv_nama.text = outlet.name
                tv_alamat.text = outlet.address
                tv_telepon.text = outlet.phone
                Glide.with(itemView.context).load(outlet.image).into(img_outlet)
            }
        }
    }

    override fun onBindViewHolder(holder: SyncOutletViewHolder, position: Int) {
        holder.bind(listOutlet[position])
    }

    fun setListOutlet(outlets: List<Outlet>){
        this.listOutlet.clear()
        this.listOutlet = outlets as MutableList<Outlet>
        notifyDataSetChanged()
    }

    fun addOutlet(outlet: Outlet){
        if(!listOutlet.contains(outlet)){
            listOutlet.add(outlet)
        } else {
            val index = listOutlet.indexOf(outlet)
            if(outlet.isDeleted){
                listOutlet.removeAt(index)
            } else {
                listOutlet[index] = outlet
            }
        }
        notifyDataSetChanged()
    }

    fun getItem() : Int = this.listOutlet.size
}