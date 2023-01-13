package com.mailerlite.sdk.exceptions;

import java.util.HashMap;

public class JsonResponseError {

    public String message = "";
    public HashMap<String, String[]> errors = new HashMap<String, String[]>();
}
