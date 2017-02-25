package com.iamwent.gank.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public class GankRecentSearchProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "com.iamwent.gank.GankRecentSearch";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public GankRecentSearchProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
