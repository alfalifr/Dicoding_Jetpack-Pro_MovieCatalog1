package sidev.app.course.dicoding.moviecatalog1.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import sidev.app.course.dicoding.moviecatalog1.R
import sidev.app.course.dicoding.moviecatalog1.databinding.DetailPageBinding
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.repository.ShowApiRepo
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Config
import sidev.app.course.dicoding.moviecatalog1.util.Util.getDurationString
import sidev.app.course.dicoding.moviecatalog1.viewmodel.ShowDetailViewModel
import java.lang.Exception

class DetailActivity: AppCompatActivity() {
    private lateinit var binding: DetailPageBinding
    private lateinit var show: Show
    private lateinit var showType: Const.ShowType
    private lateinit var vm: ShowDetailViewModel
    private val showRepo = Config.defaultShowRepo

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

        vm = ShowDetailViewModel.getInstance(this, application, showRepo, showType).apply {
            onPreAsyncTask {
                Config.incIdling()
                showError(false)
                showLoading()
            }
            onCallNotSuccess { code, e ->
                showLoading(false)
                showError(true, code, e)
                Config.decIdling()
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
                showError(false)
                showLoading(false)
                Config.decIdling()
            }
            downloadShowDetail(show.id)
        }
    }

    private fun showLoading(show: Boolean = true)= binding.apply {
        if(show){
            pbLoading.visibility = View.VISIBLE
            tvOverview.visibility = View.GONE
            tvOverviewContent.visibility = View.GONE
        } else {
            pbLoading.visibility = View.GONE
            tvOverview.visibility = View.VISIBLE
            tvOverviewContent.visibility = View.VISIBLE
        }
    }

    private fun showError(show: Boolean = true, code: Int = -1, e: Exception? = null) = binding.apply {
        if(show){
            tvOverview.visibility= View.GONE
            tvOverviewContent.visibility= View.GONE
            tvError.visibility= View.VISIBLE
            val eClass = if(e != null) e::class.java.simpleName else "null"
            binding.tvError.text = getString(R.string.error_data, "$eClass ($code)", e?.message ?: "null")
        } else {
            tvOverview.visibility= View.VISIBLE
            tvOverviewContent.visibility= View.VISIBLE
            tvError.visibility= View.GONE
        }
    }
}