package com.ulan.app.munduz.developer

import android.content.SearchRecentSuggestionsProvider

class SuggestionProvider: SearchRecentSuggestionsProvider {

    val AUTHORITY = "com.ulan.app.munduz.SuggestionProvider"
    val MODE = 123

    constructor(){
        setupSuggestions(AUTHORITY, MODE)
    }

}