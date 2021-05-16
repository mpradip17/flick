package com.kotlintest.app.model

data class ImageSearchModel(
    var photos: Photos = Photos(),
    var stat: String = ""
) {
    data class Photos(
        var page: String = "0",
        var pages: String = "0",
        var perpage: String = "0",
        var total: String = "0",
        var photo: ArrayList<Photo> = ArrayList()
    ) {
        data class Photo(
            var id: String = "",
            var owner: String = "",
            var secret: String = "",
            var server: String = "",
            var farm: String = "0",
            var title: String = "",
            var ispublic: String = "0",
            var isfriend: String = "0",
            var isfamily: String = "0"
        )
    }
}