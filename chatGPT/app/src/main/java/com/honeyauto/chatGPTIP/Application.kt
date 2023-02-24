package com.honeyauto.chatGPTIP


import com.honeyauto.chatGPTIP.model.DetailWordModel

class MyGlobals {
    //categoryActivity에서 해당 밸류 있으면 바로 worddialog로 넘어감
    var checkLanguage: String? = null
    //언어 선택에서만사용
    var languageList: List<String>? = null
    var buttontext: List<String>? = null

    //categorydetail, dialogcategory, wordlist 데이터맵
    var detailModel: DetailWordModel? = null

    //category detail fragment -> wordlist 로 갈때 키워드 전역번수 선언
    var middleKeyword: String? = null


    companion object {
        @get:Synchronized
        var instance: MyGlobals? = null
            get() {
                if (null == field) {
                    field = MyGlobals()
                }
                return field
            }
            private set
    }
}
