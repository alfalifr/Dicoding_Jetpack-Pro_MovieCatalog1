package sidev.app.course.dicoding.moviecatalog1

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.junit.Test
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util.getDouble
import sidev.app.course.dicoding.moviecatalog1.util.Util.getString
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.jvm.tool.`fun`.responseStr
import java.lang.IllegalStateException
import java.net.HttpURLConnection
import java.net.URL

class CobUnitTest {
    @Test
    fun jsonCallTest(){
        val type = Const.ShowType.MOVIE
        val url = type.getPopularUrl()

        val client = OkHttpClient()
        val req = Request.Builder()
            .url(url)
            .build()

        val res = client.newCall(req).execute()
        val response = res.body?.string()
/*
        val conn = URL(url).openConnection() as HttpURLConnection

        val response = conn.responseStr()
            ?: throw IllegalStateException("Response null")
 */

        //prine("response= $response")
///*
        val json = JsonParser.parseString(response).asJsonObject
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
        val str = movies.joinToString(separator = "\n")
        prin("str= $str")
// */
    }
}