package sidev.app.course.dicoding.moviecatalog1

class Cob {
    private fun privateFun(str: String) = "str= $str"
    private fun privateFun(int: Int) = "int= $int"
    fun publicFun(str: String) = privateFun(str)
}