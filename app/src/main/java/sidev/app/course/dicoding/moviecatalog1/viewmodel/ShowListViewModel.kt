package sidev.app.course.dicoding.moviecatalog1.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.JsonParser
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util
import sidev.app.course.dicoding.moviecatalog1.util.Util.getDouble
import sidev.app.course.dicoding.moviecatalog1.util.Util.getString
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.console.prine

class ShowListViewModel(
    c: Application?,
    private val type: Const.ShowType,
): AsyncVm(c) {

    companion object {
        fun getInstance(
            owner: ViewModelStoreOwner,
            c: Application?,
            type: Const.ShowType,
        ): ShowListViewModel = ViewModelProvider(
            owner,
            object: ViewModelProvider.Factory {
                @Suppress(SuppressLiteral.UNCHECKED_CAST)
                override fun <T : ViewModel?> create(modelClass: Class<T>): T = ShowListViewModel(c, type) as T
            }
        ).get(ShowListViewModel::class.java)
    }

    val showList: LiveData<out List<Show>>
        get()= _showList
    private val _showList: MutableLiveData<MutableList<Show>> = MutableLiveData()


    fun downloadShowPopularList(page: Int = 1, forceDownload: Boolean = false){
        if(!forceDownload && _showList.value != null) return
        cancelJob()
        doOnPreAsyncTask()
        job = Util.httpGet(
            ctx,
            type.getPopularUrl(page = page), //Const.getTvPopularUrl(page = page),
            ::doCallNotSuccess
        ){ _, content ->
            content.parseShowListTo(_showList)
        }
    }

    private fun String.parseShowListTo(liveData: MutableLiveData<MutableList<Show>>){
        val json = JsonParser.parseString(this).asJsonObject
        val jsonArray = json.getAsJsonArray(Const.KEY_RESULTS)
        val movies = ArrayList<Show>(jsonArray.size())
        for(i in 0 until jsonArray.size()){
            val movieJson = jsonArray[i].asJsonObject
            movies += Show(
                movieJson.getString(Const.KEY_ID),
                (if(movieJson.has(Const.KEY_TITLE)) movieJson.getString(Const.KEY_TITLE)
                else movieJson.getString(Const.KEY_NAME)),
                movieJson.getString(Const.KEY_IMG),
                (if(movieJson.has(Const.KEY_RELEASE)) movieJson.getString(Const.KEY_RELEASE)
                else movieJson.getString(Const.KEY_FIRST_AIR_DATE)),
                movieJson.getDouble(Const.KEY_RATING),
            )
        }
        liveData.postValue(movies)
    }
}