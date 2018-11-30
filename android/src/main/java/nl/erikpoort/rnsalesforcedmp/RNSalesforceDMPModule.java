package nl.erikpoort.rnsalesforcedmp;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

/**
 * Created by erik on 30/11/2018.
 * Erik Poort 2018
 */

class RNSalesforceDMPModule extends ReactContextBaseJavaModule {
    private static final String SALESFORCE_DMP = "RNSalesforceDMPModule";

    private static final String ERROR_GENERAL = "general_error";

    RNSalesforceDMPModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return SALESFORCE_DMP;
    }
}
