package sidev.app.course.dicoding.moviecatalog1.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.google.gson.JsonParser
import org.json.JSONObject
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.model.ShowDetail
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util
import sidev.app.course.dicoding.moviecatalog1.util.Util.forEach
import sidev.app.course.dicoding.moviecatalog1.util.Util.getIntOrNull
import sidev.app.course.dicoding.moviecatalog1.util.Util.getString
import sidev.lib.`val`.SuppressLiteral

class ShowDetailViewModel private constructor(
    c: Context,
    private val show: Show,
    private val type: Const.ShowType,
): TemplateVm(c) {

    companion object {
        fun getInstance(
            owner: ViewModelStoreOwner,
            c: Context,
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

    fun downloadShowDetail(id: String){
        cancelJob()
        doOnPreAsyncTask()
        job = Util.httpGet(
            ctx!!,
            type.getDetailUrl(id),
        ) { _, content ->
            val json = JsonParser.parseString(content).asJsonObject
            val genreArray = json.getAsJsonArray(Const.KEY_GENRES)
            val genres = ArrayList<String>(genreArray.size())
            genreArray.forEach {
                genres += it.asJsonObject.getString(Const.KEY_NAME)
            }
            _showDetail.value = ShowDetail(
                show, genres,
                json.getIntOrNull(Const.KEY_MOVIE_DURATION),
                json.getString(Const.KEY_TAGLINE),
                json.getString(Const.KEY_OVERVIEW),
                json.getString(Const.KEY_BACKDROP),
            )
        }
    }
}