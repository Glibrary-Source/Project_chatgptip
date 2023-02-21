package com.honeyauto.chatGPTIP

class MyGlobals {
    var checkLanguage: String? = null
    var currentFragment: String? = null

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
