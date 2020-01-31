package com.rakha.simpleprojecttemplate.utils;

import com.rakha.simpleprojecttemplate.models.BasicResponse;
import com.rakha.simpleprojecttemplate.services.ServiceGenerator;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created By rakha
 * 2019-12-15
 */
public class RetrofitErrorUtils {

    public static BasicResponse<Object> parseError(Response<?> response) {
        Converter<ResponseBody, BasicResponse<Object>> converter =
                ServiceGenerator.retrofit
                        .responseBodyConverter(BasicResponse.class, new Annotation[0]);

        BasicResponse<Object> error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new BasicResponse<>();
        }
//        Gson gson = new Gson();
//        Type type = new TypeToken<BasicResponse>() {}.getType();
//        error = gson.fromJson(response.errorBody().charStream(),type);

        return error;
    }
}