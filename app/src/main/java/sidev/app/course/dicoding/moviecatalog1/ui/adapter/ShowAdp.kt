package sidev.app.course.dicoding.moviecatalog1.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.app.course.dicoding.moviecatalog1.databinding.ItemMainListBinding
import sidev.app.course.dicoding.moviecatalog1.model.Show

class ShowAdp: RecyclerView.Adapter<ShowAdp.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMainListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Show) = binding.apply {
            tvTitle.text = data.title
            tvRelease.text = data.getFormattedDate()
            tvPb.text = root.context.getString(R.string.percent, data.rating)
            pb.max = 100
            pb.progress = data.rating.times(10).toInt()
            Glide.with(iv)
                .load(data.imgUrl_300x450())
                .into(iv)
            root.setOnClickListener {
                onItemClick?.invoke(adapterPosition, data)
            }
        }
    }

    var dataList: List<Show>? = null
        set(v){
            field = v
            notifyDataSetChanged()
        }
    private var onItemClick: ((pos: Int, data: Show) -> Unit)? = null
    fun setOnItemClick(l: ((pos: Int, data: Show) -> Unit)?) {
        onItemClick = l
    }

    override fun getItemCount(): Int = dataList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList!![position]
        holder.bind(data)
    }
}