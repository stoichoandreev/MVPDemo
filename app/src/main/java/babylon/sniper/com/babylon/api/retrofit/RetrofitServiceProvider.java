package babylon.sniper.com.babylon.api.retrofit;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import babylon.sniper.com.babylon.BuildConfig;
import babylon.sniper.com.babylon.api.interfaces.ApiService;
import babylon.sniper.com.babylon.constants.Preferences;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides a single instance of {@link retrofit2.Retrofit}.
 */
public class RetrofitServiceProvider {

    private static ApiService instance;
    /**
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "max-age=60")
                    .build();
        }
    };

    public static ApiService getApiServiceInstance(Context context) {
        if (instance != null) {
            return instance;
        }

        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.RETROFIT_LOG_LEVEL);
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        File cacheDir = new File(context.getCacheDir(), UUID.randomUUID().toString());
        Cache cache = new Cache(cacheDir, 1024);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();


        instance = new Retrofit.Builder()
                .baseUrl(Preferences.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(ApiService.class);

        return instance;
    }
}
