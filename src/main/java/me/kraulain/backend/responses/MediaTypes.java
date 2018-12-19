package com.afgrey.solarlinx.common.responses;

import static io.vertx.core.http.HttpHeaders.createOptimized;

/**
 * Created by Afgrey Development Team.
 */
public class MediaTypes {

    /**
     * application/json;charset=utf-8 header value
     */
    public static final CharSequence APPLICATION_JSON = createOptimized(
            "application/json;charset=utf-8");
}
