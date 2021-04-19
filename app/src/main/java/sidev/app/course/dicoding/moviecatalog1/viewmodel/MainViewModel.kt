package sidev.app.course.dicoding.moviecatalog1.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util

class MainViewModel(c: Context?): TemplateVm(c) {
    val tvShowList: LiveData<out List<Show>>
        get()= _tvShowList
    val movieList: LiveData<out List<Show>>
        get()= _movieList

    private val _tvShowList: MutableLiveData<MutableList<Show>> = MutableLiveData()
    private val _movieList: MutableLiveData<MutableList<Show>> = MutableLiveData()


    fun downloadMoviePopularList(page: Int){
        doOnPreAsyncTask()
        Util.httpGet(
            ctx!!,
            Const.getMoviePopularUrl(page = page),
        ){ code, content ->
            content.parseShowListTo(_movieList)
        }
    }

    fun downloadTvPopularList(page: Int){
        doOnPreAsyncTask()
        Util.httpGet(
            ctx!!,
            Const.getTvPopularUrl(page = page),
        ){ code, content ->
            content.parseShowListTo(_tvShowList)
        }
    }

    private fun String.parseShowListTo(liveData: MutableLiveData<out List<Show>>){
        val json = JSONObject(this)
        val jsonArray = json.getJSONArray(Const.KEY_RESULTS)
        val movies = ArrayList<Show>(jsonArray.length())
        for(i in 0 until jsonArray.length()){
            val movieJson = jsonArray.getJSONObject(i)
            movies += Show(
                movieJson.getString(Const.KEY_ID),
                movieJson.getString(Const.KEY_TITLE),
                movieJson.getString(Const.KEY_IMG),
                movieJson.getString(Const.KEY_RELEASE),
                movieJson.getDouble(Const.KEY_RATING),
            )
        }
        liveData.value = movies
    }
}