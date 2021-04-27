package sidev.app.course.dicoding.moviecatalog1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.JsonParser
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import sidev.app.course.dicoding.moviecatalog1.model.Show
import sidev.app.course.dicoding.moviecatalog1.repository.ShowApiRepo
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util.getDouble
import sidev.app.course.dicoding.moviecatalog1.util.Util.getString
import sidev.app.course.dicoding.moviecatalog1.viewmodel.ShowListViewModel
import sidev.lib.console.prin
import sidev.lib.console.prine
import java.io.IOException

class CobUnitTest {
    @Test
    fun jsonCallTest() {
        val type = Const.ShowType.MOVIE
        val url = type.getPopularUrl()

        val client = OkHttpClient()
        val req = Request.Builder()
            .url(url)
            .build()
        //val res =
        client.newCall(req)
            .enqueue(object: Callback{
                /**
                 * Called when the request could not be executed due to cancellation, a connectivity problem or
                 * timeout. Because networks can fail during an exchange, it is possible that the remote server
                 * accepted the request before the failure.
                 */
                override fun onFailure(call: Call, e: IOException) {
                    prine("call= $call")
                    prine("e= $e")
                }

                /**
                 * Called when the HTTP response was successfully returned by the remote server. The callback may
                 * proceed to read the response body with [Response.body]. The response is still live until its
                 * response body is [closed][ResponseBody]. The recipient of the callback may consume the response
                 * body on another thread.
                 *
                 * Note that transport-layer success (receiving a HTTP response code, headers and body) does not
                 * necessarily indicate application-layer success: `response` may still indicate an unhappy HTTP
                 * response code like 404 or 500.
                 */
                override fun onResponse(call: Call, res: Response) {
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
            })
        runBlocking { delay(10000) }
    }

    @Test
    fun mockTest(){
        val cob = Cob()
        val mock = Mockito.spy(cob)
/*
        val privateFun = MemberMatcher.method(
            Cob::class.java,
            "privateFun",
            String::class.java
        )
        doReturn("str 2 =")
            .`when`(mock, privateFun)
            .withArguments<Any>(Matchers.any(String::class.java))

        val ret = mock.publicFun("a")
        prin(ret)
// */
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun vmTesting() = runBlocking {
        //val vm = ShowListViewModel(null, Const.ShowType.MOVIE)
        val mock = ShowListViewModel(null, ShowApiRepo, Const.ShowType.MOVIE) //Mockito.spy(vm)
        mock.onCallNotSuccess { code, e ->
            prine("onCallNotSuccess code= $code e= $e")
        }
        mock.downloadShowPopularList()
        delay(10000)
        val res = mock.showList.value?.joinToString(separator = "\n")
        prin(res)
    }
}
/*
 private class Cob {
     private fun privateFun(str: String) = "str= $str"
     private fun privateFun(int: Int) = "int= $int"
     fun publicFun(str: String) = privateFun(str)
 }

 */