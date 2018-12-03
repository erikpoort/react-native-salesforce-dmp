package nl.erikpoort.rnsalesforcedmp;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.krux.androidsdk.aggregator.KruxConsentCallback;
import com.krux.androidsdk.aggregator.KruxEventAggregator;
import com.krux.androidsdk.aggregator.KruxSegments;

import java.util.HashMap;

/**
 * Created by erik on 30/11/2018.
 * Erik Poort 2018
 */

class RNSalesforceDMPModule extends ReactContextBaseJavaModule implements KruxConsentCallback, KruxSegments {
    private static final String SALESFORCE_DMP = "RNSalesforceDMPModule";

    private final Application _application;
    private boolean _initialised;
    private Promise _consentSetPromise;
    private Promise _consentGetPromise;
    private Promise _consumerRemovePromise;
    private Promise _consumerPortabilityPromise;
    private String _segments;

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

        KruxEventAggregator.initialize(_application, configId, this, debug, this);
        promise.resolve(true);
        _initialised = true;
    }

    @ReactMethod
    public void getSegments(final Promise promise) {
        if (!_initialised)
        {
            promise.reject(SALESFORCE_DMP, "Salesforce DMP is not initialised");
            return;
        }

        if (_segments == null) {
            promise.reject(SALESFORCE_DMP, "No segments loaded yet");
        } else {
            promise.resolve(_segments);
        }
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

    @ReactMethod
    public void consentSetRequest(final ReadableMap attributes, final Promise promise) {
        if (!_initialised) {
            promise.reject(SALESFORCE_DMP, "Salesforce DMP is not initialised");
            return;
        }
        if (attributes == null || attributes.toHashMap().isEmpty()) {
            promise.reject(SALESFORCE_DMP, "Attributes can't be empty");
            return;
        }
        if (_consentSetPromise != null) {
            promise.reject(SALESFORCE_DMP, "Consent set request already active");
            return;
        }

        Bundle bundle = mapToBundle(attributes.toHashMap());
        _consentSetPromise = promise;
        KruxEventAggregator.consentSetRequest(bundle);
    }

    @ReactMethod
    public void consentGetRequest(final ReadableMap attributes, final Promise promise) {
        if (!_initialised) {
            promise.reject(SALESFORCE_DMP, "Salesforce DMP is not initialised");
            return;
        }
        if (attributes == null || attributes.toHashMap().isEmpty()) {
            promise.reject(SALESFORCE_DMP, "Attributes can't be empty");
            return;
        }
        if (_consentGetPromise != null) {
            promise.reject(SALESFORCE_DMP, "Consent get request already active");
            return;
        }

        Bundle bundle = mapToBundle(attributes.toHashMap());
        _consentGetPromise = promise;
        KruxEventAggregator.consentGetRequest(bundle);
    }

    @ReactMethod
    public void consumerRemoveRequest(final ReadableMap attributes, final Promise promise) {
        if (!_initialised) {
            promise.reject(SALESFORCE_DMP, "Salesforce DMP is not initialised");
            return;
        }
        if (attributes == null || attributes.toHashMap().isEmpty()) {
            promise.reject(SALESFORCE_DMP, "Attributes can't be empty");
            return;
        }
        if (_consumerRemovePromise != null) {
            promise.reject(SALESFORCE_DMP, "Consumer remove request already active");
            return;
        }

        Bundle bundle = mapToBundle(attributes.toHashMap());
        _consumerRemovePromise = promise;
        KruxEventAggregator.consumerRemoveRequest(bundle);
    }

    @ReactMethod
    public void consumerPortabilityRequest(final ReadableMap attributes, final Promise promise) {
        if (!_initialised) {
            promise.reject(SALESFORCE_DMP, "Salesforce DMP is not initialised");
            return;
        }
        if (attributes == null || attributes.toHashMap().isEmpty()) {
            promise.reject(SALESFORCE_DMP, "Attributes can't be empty");
            return;
        }
        if (_consumerPortabilityPromise != null) {
            promise.reject(SALESFORCE_DMP, "Consumer portability request already active");
            return;
        }

        Bundle bundle = mapToBundle(attributes.toHashMap());
        _consumerPortabilityPromise = promise;
        KruxEventAggregator.consumerPortabilityRequest(bundle);
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

    @Override
    public void handleConsentSetResponse(String s) {
        if (_consentSetPromise == null) {
            Log.w(SALESFORCE_DMP, "No promise to resolve");
            return;
        }

        _consentSetPromise.resolve(s);
        _consentSetPromise = null;
    }

    @Override
    public void handleConsentSetError(String s) {
        if (_consentSetPromise == null) {
            Log.w(SALESFORCE_DMP, "No promise to reject");
            return;
        }

        _consentSetPromise.reject(SALESFORCE_DMP, s);
        _consentSetPromise = null;
    }

    @Override
    public void handleConsentGetResponse(String s) {
        if (_consentGetPromise == null) {
            Log.w(SALESFORCE_DMP, "No promise to resolve");
            return;
        }

        _consentGetPromise.resolve(s);
        _consentGetPromise = null;
    }

    @Override
    public void handleConsentGetError(String s) {
        if (_consentGetPromise == null) {
            Log.w(SALESFORCE_DMP, "No promise to reject");
            return;
        }

        _consentGetPromise.reject(SALESFORCE_DMP, s);
        _consentGetPromise = null;
    }

    @Override
    public void handleConsumerRemoveResponse(String s) {
        if (_consumerRemovePromise == null) {
            Log.w(SALESFORCE_DMP, "No promise to resolve");
            return;
        }

        _consumerRemovePromise.resolve(s);
        _consumerRemovePromise = null;
    }

    @Override
    public void handleConsumerRemoveError(String s) {
        if (_consumerRemovePromise == null) {
            Log.w(SALESFORCE_DMP, "No promise to reject");
            return;
        }

        _consumerRemovePromise.reject(SALESFORCE_DMP, s);
        _consumerRemovePromise = null;
    }

    @Override
    public void handleConsumerPortabilityResponse(String s) {
        if (_consumerPortabilityPromise == null) {
            Log.w(SALESFORCE_DMP, "No promise to resolve");
            return;
        }

        _consumerPortabilityPromise.resolve(s);
        _consumerPortabilityPromise = null;
    }

    @Override
    public void handleConsumerPortabilityError(String s) {
        if (_consumerPortabilityPromise == null) {
            Log.w(SALESFORCE_DMP, "No promise to reject");
            return;
        }

        _consumerPortabilityPromise.reject(SALESFORCE_DMP, s);
        _consumerPortabilityPromise = null;
    }

    @Override
    public void getSegments(String s) {
        _segments = s;
    }
}
