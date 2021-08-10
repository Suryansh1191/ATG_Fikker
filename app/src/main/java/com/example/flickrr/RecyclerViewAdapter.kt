package com.example.flickrr


import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.flickrr.Model.Photo
import android.net.Uri as Uri1


class RecyclerViewAdapter(private val context: Context, data: List<Photo>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    private  var datalist =data
    var mydownloadId:Long=0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         var image:ImageView = itemView.findViewById(R.id.Image)
        var download:Button = itemView.findViewById(R.id.download)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.gallery, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val data=datalist[position]
        Glide.with(context).load(data.url_s).placeholder(R.drawable.placeholder).listener(object :
            RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        }).error(R.drawable.share).into(holder.image)




        holder.image.setOnClickListener {
            Toast.makeText(context, "Item Number $position Clicked - ", Toast.LENGTH_SHORT).show()

        }

        holder.download.setOnClickListener {
            val request=DownloadManager.Request(Uri1.parse(data.url_s)).
            setTitle(data.title).
            setDescription("Image Downloading").
            setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, data.title).
            setAllowedOverMetered(true)
            Toast.makeText(context, "IMG Downloaded", Toast.LENGTH_SHORT).show()

           val dm= context.getSystemService(Context.DOWNLOAD_SERVICE)  as DownloadManager
            mydownloadId=dm.enqueue(request)
        }

        val br=object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
            }

        }
        context.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))



    }




    override fun getItemCount(): Int {
        return datalist.size
    }

}

