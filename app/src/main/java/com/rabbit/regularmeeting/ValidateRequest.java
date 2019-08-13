package com.rabbit.regularmeeting;

        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;

        import java.util.HashMap;
        import java.util.Map;

public class ValidateRequest extends StringRequest {
    final static private String URL = "http://211.170.81.147/JcastMesPortable/UserValidate.php";
    private Map<String, String> paramters;

    public ValidateRequest(String userID, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        paramters = new HashMap<>();
        paramters.put("userID", userID);
    }

    @Override
    public Map<String, String> getParams()
    {
        return paramters;
    }
}
