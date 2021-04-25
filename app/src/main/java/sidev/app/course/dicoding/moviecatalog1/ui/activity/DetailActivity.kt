package sidev.app.course.dicoding.moviecatalog1.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.app.course.dicoding.moviecatalog1.databinding.DetailPageBinding
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util.getDurationString
import sidev.app.course.dicoding.moviecatalog1.viewmodel.ShowDetailViewModel

class DetailActivity: AppCompatActivity() {
    private lateinit var binding: DetailPageBinding
    private lateinit var show: Show
    private lateinit var showType: Const.ShowType
    private lateinit var vm: ShowDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.apply {
            show = intent.getSerializableExtra(Const.KEY_SHOW) as Show
            showType = intent.getSerializableExtra(Const.KEY_TYPE) as Const.ShowType
        }

        binding.apply {
            tvTitle.text= show.title
            tvRelease.text= show.getFormattedDate()
            tvPb.text = getString(R.string.percent, show.rating)
            pbRating.max = 100
            pbRating.progress = show.rating.times(10).toInt()
            Glide.with(this@DetailActivity)
                .load(show.imgUrl_300x450())
                .into(ivPoster)
        }

        vm = ShowDetailViewModel.getInstance(this, application, show, showType).apply {
            onPreAsyncTask {
                showLoading()
            }
            showDetail.observe(this@DetailActivity){
                if(it != null){
                    binding.apply {
                        tvDuration.text = getDurationString(it) ?: run {
                            tvDuration.visibility = View.GONE
                            "null"
                        }
                        tvGenres.text = it.genres.joinToString()
                        tvTagline.text = it.tagline
                        tvOverviewContent.text = it.overview
                        Glide.with(this@DetailActivity)
                            .load(it.backdropImgUrl_533x300())
                            .into(ivBg)
                    }
                }
                showLoading(false)
            }
            downloadShowDetail()
        }
    }

    private fun showLoading(show: Boolean = true)= binding.apply {
        pb.visibility = if(show) View.VISIBLE else View.GONE
    }
}