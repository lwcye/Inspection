package com.cmcc.inspection.widget.x5.utils;


public interface WebViewJavascriptBridge {

    public void send(String data);

    public void send(String data, CallBackFunction responseCallback);


}
