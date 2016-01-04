package com.test.api;

/**
 * Class for constants, used for URL completing for REST requests
 */
public final class ApiSettings {

    public static final String SCHEME = "https://";

    public static final String HOSTNAME = "openapi.etsy.com/";

    public static final String ETSY_API_KEY = "l6pdqjuf7hdf97h1yvzadfce";

    public static final String SERVER = SCHEME + HOSTNAME;

    public static final String HEADER_AUTH_TOKEN = "Authorization";

    public static final String QUERY_API_KEY = "api_key";

    public static final String PARAM_REPOS_TYPE = "type";

    public static final String CATEGORIES = "v2/taxonomy/categories";

    public static final String LISTINGS = "v2/listings";

    public static final String LISTING_ID = "listing_id";

    public static final String SEARCH_LISTINGS = LISTINGS + "/active";

    public static final String IMAGES_LISTINGS = LISTINGS + "/{" + LISTING_ID + "}" + "/images";

    public static final String CATEGORY_QUERY = "category";

    public static final String KEYWORDS_QUERY = "keywords";

    public static final String PAGE = "page";

    public static final String INCLUDES = "includes";

    public static final String IMAGES = "Images";


}