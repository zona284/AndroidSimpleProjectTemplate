package com.rakha.simpleprojecttemplate.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.rakha.simpleprojecttemplate.models.BasicResponse;
import com.rakha.simpleprojecttemplate.utils.RetrofitErrorUtils;
import com.rakha.simpleprojecttemplate.utils.UiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By rakha
 * 2019-12-15
 */
public abstract class ApiCallback<T> implements Callback<BasicResponse<T>> {
    private Context context;

    public ApiCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(@NonNull Call<BasicResponse<T>> call, @NonNull Response<BasicResponse<T>> response) {
        BasicResponse<T> basicResponse = response.body();
        if (response.isSuccessful()) {
            Log.d("api_success", basicResponse.getMessage().getTitle());
            onSuccess(call, basicResponse);
        } else {
            if (response.code() == 401) {
                Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                UiUtils.doLogout(context);
            } else {
                try {
                    BasicResponse<Object> baseResponse = RetrofitErrorUtils.parseError(response);
                    Object body = baseResponse.getMessage().getBody();
                    if (baseResponse != null) {
                        Log.d("api_failed", getErrMessage(body));
                        onFailed(call, getErrMessage(body));
                    } else {
                        Log.d("api_failed", "Error " + response.code() + ": " + response.message());
                        onFailed(call, "Error " + response.code() + ": " + response.message());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailed(call, "Error " + response.code() + ": " + response.message());
                }
            }

        }
    }

    @Override
    public void onFailure(@NonNull Call<BasicResponse<T>> call, @NonNull Throwable t) {
        Log.d("api_failed", "Failure " + t.getMessage());
        onFailed(call, t.getMessage());
    }

    private String getErrMessage(Object body) {
        String errMessage = "";
        if (body instanceof String) {
            errMessage = body.toString();
        } else if (body instanceof ArrayList) {
            ArrayList<String> messages = (ArrayList<String>) body;

            int idx = 0;
            for (String message : messages) {
                String messageSplit = message.split(":")[1];

                if (idx == 0) {
                    errMessage += messageSplit;
                } else {

                }
            }
        }

        return errMessage;
    }

    abstract public void onSuccess(Call<BasicResponse<T>> call, BasicResponse<T> response);

    abstract public void onFailed(Call<BasicResponse<T>> call, String message);
}