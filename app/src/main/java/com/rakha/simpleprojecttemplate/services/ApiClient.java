package com.rakha.simpleprojecttemplate.services;

import android.content.Context;

import com.rakha.simpleprojecttemplate.BuildConfig;
import com.rakha.simpleprojecttemplate.utils.UiUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created By rakha
 * 2019-12-15
 */
public class ApiClient {
    private static final String BASE_URL = BuildConfig.BASE_URL;

    public static final String HEADER_CACHE_CONTROL = "Cache-Control";

    private static Retrofit retrofit;
    private static OkHttpClient client;

    /**
     * GET OKHTTP CLIENT WITH TLSSOCKETFACTORY TLS1.2 FORCE ENABLED
     *
     * */
    private static OkHttpClient.Builder getOkHttpClientWithTLSFactory(){
        try {
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                    .build();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder
                    .connectionSpecs(Arrays.asList(spec, ConnectionSpec.CLEARTEXT))
//                    .sslSocketFactory(new TLSSocketFactory())
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS);

            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <S> S getInstance(Context context, Class<S> serviceClass) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpBuilder = getOkHttpClientWithTLSFactory();

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        try {
            okHttpBuilder
                    .addInterceptor(getCacheInterceptor(context))
                    .cache(new Cache(File.createTempFile("_abj","abj",context.getCacheDir()),20*1024*1024));
        } catch (IOException e) {
            e.printStackTrace();
        }

        client = okHttpBuilder.build();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    private static Interceptor getCacheInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                if (!UiUtils.isConnected(context)) {
                    Request request = chain.request();

                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(1, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();

                    return chain.proceed(request);
                } else {
                    Response response = chain.proceed(chain.request());

                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxAge(1, TimeUnit.HOURS)
                            .build();

                    return response.newBuilder()
                            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                            .build();
                }
            }
        };
    }

    private static Cache getCache(Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(context.getCacheDir(), cacheSize);
    }
}