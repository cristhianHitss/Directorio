package com.bbva.hitss.directorio.Http.Handlers;

import org.json.JSONObject;

public interface AsyncResult
{
    void onResult(JSONObject object);
}