package nl.erikpoort.rnsalesforcedmp;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.krux.androidsdk.aggregator.KruxEventAggregator;

import java.util.HashMap;

/**
 * Created by erik on 30/11/2018.
 * Erik Poort 2018
 */

class RNSalesforceDMPModule extends ReactContextBaseJavaModule {
    private static final String SALESFORCE_DMP = "RNSalesforceDMPModule";

    private final Application _application;
    private boolean _initialised;

    RNSalesforceDMPModule(ReactApplicationContext reactContext, Application application) {
        super(reactContext);

        this._application = application;
    }

    @Override
    public String getName() {
        return SALESFORCE_DMP;
    }


    @ReactMethod
    public void setupTracker(final String configId, final boolean debug, final Promise promise)
    {
        if (_initialised)
        {
            promise.reject(SALESFORCE_DMP, "Salesforce DMP is already initialised");
            return;
        }
        if (configId == null || configId.isEmpty())
        {
            promise.reject(SALESFORCE_DMP, "Your configId is empty");
            return;
        }

        KruxEventAggregator.initialize(_application, configId, null, debug);
        promise.resolve(true);
        _initialised = true;
    }

    @ReactMethod
    public void trackPageView(final String name, final ReadableMap pageAttributes, final ReadableMap userAttributes, final Promise promise)
    {
        if (!_initialised)
        {
            promise.reject(SALESFORCE_DMP, "Salesforce DMP is not initialised");
            return;
        }
        if (name == null || name.isEmpty())
        {
            promise.reject(SALESFORCE_DMP, "Name can't be empty");
            return;
        }

        Bundle pageBundle = mapToBundle(pageAttributes.toHashMap());
        if (userAttributes == null || userAttributes.toHashMap().isEmpty()) {
            KruxEventAggregator.trackPageView(name, pageBundle, Bundle.EMPTY);
        } else {
            Bundle userBundle = mapToBundle(userAttributes.toHashMap());
            KruxEventAggregator.trackPageView(name, pageBundle, userBundle);
        }
        promise.resolve(true);
    }

    @ReactMethod
    public void fireEvent(final String event, final ReadableMap attributes, final Promise promise)
    {
        if (!_initialised)
        {
            promise.reject(SALESFORCE_DMP, "Salesforce DMP is not initialised");
            return;
        }
        if (event == null || event.isEmpty())
        {
            promise.reject(SALESFORCE_DMP, "Event can't be empty");
            return;
        }

        Bundle bundle;
        if (attributes == null) {
            bundle = Bundle.EMPTY;
        } else {
            bundle = mapToBundle(attributes.toHashMap());
        }

        KruxEventAggregator.fireEvent(event, bundle);
        promise.resolve(true);
    }

    @ReactMethod
    public void trackTransaction(final ReadableMap attributes, final Promise promise)
    {
        if (!_initialised)
        {
            promise.reject(SALESFORCE_DMP, "Salesforce DMP is not initialised");
            return;
        }
        if (attributes == null || attributes.toHashMap().isEmpty()) {
            promise.reject(SALESFORCE_DMP, "Attributes can't be empty");
            return;
        }

        Bundle bundle = mapToBundle(attributes.toHashMap());
        KruxEventAggregator.transaction(bundle);
        promise.resolve(true);
    }

    private Bundle mapToBundle(HashMap<String, Object> hashMap)
    {
        final Bundle bundle = new Bundle();
        for (String key : hashMap.keySet()) {
            Object value = hashMap.get(key);
            if (value instanceof String) {
                bundle.putString(key, (String) value);
            } else {
                Log.w(SALESFORCE_DMP, "Value for key " + key + " is not a string and ignored.");
            }
        }
        return bundle;
    }
}
