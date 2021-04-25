package sidev.app.course.dicoding.moviecatalog1.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.JsonParser
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util
import sidev.app.course.dicoding.moviecatalog1.util.Util.getIntOrNull
import sidev.app.course.dicoding.moviecatalog1.util.Util.getString
import sidev.lib.`val`.SuppressLiteral

class ShowDetailViewModel(
    c: Application?,
    private val show: Show,
    private val type: Const.ShowType,
): AsyncVm(c) {

    companion object {
        fun getInstance(
            owner: ViewModelStoreOwner,
            c: Application?,
            show: Show,
            type: Const.ShowType,
        ): ShowDetailViewModel = ViewModelProvider(
            owner,
            object: ViewModelProvider.Factory {
                @Suppress(SuppressLiteral.UNCHECKED_CAST)
                override fun <T : ViewModel?> create(modelClass: Class<T>): T = ShowDetailViewModel(c, show, type) as T
            }
        ).get(ShowDetailViewModel::class.java)
    }

    private val _showDetail: MutableLiveData<ShowDetail> = MutableLiveData()
    val showDetail: LiveData<ShowDetail>
        get()= _showDetail

    fun downloadShowDetail(forceDownload: Boolean = false){
        if(!forceDownload && _showDetail.value != null) return
        cancelJob()
        doOnPreAsyncTask()
        job = Util.httpGet(
            ctx,
            type.getDetailUrl(show.id),
            ::doCallNotSuccess
        ) { _, content ->
            val json = JsonParser.parseString(content).asJsonObject
            val genreArray = json.getAsJsonArray(Const.KEY_GENRES)
            val genres = ArrayList<String>(genreArray.size())
            genreArray.forEach {
                genres += it.asJsonObject.getString(Const.KEY_NAME)
            }
            _showDetail.postValue(
                ShowDetail(
                    show, genres,
                    json.getIntOrNull(Const.KEY_MOVIE_DURATION),
                    json.getString(Const.KEY_TAGLINE),
                    json.getString(Const.KEY_OVERVIEW),
                    json.getString(Const.KEY_BACKDROP),
                )
            )
        }
    }
}