import { NativeModules } from 'react-native';
const { RNSalesforceDMPModule } = NativeModules;

/**
* Setup tracker
* @param {string} configId Configuration Id
* @param {boolean} debugFlag This outputs debug messages while it is trying to fetch data/send data
*     to Salesforce DMP backend servers. You should turn this off before the final submission of
*     your app.
*/
function setupTracker(configId, debugFlag) {
  RNSalesforceDMPModule.setupTracker(configId, debugFlag);
}

/**
* Track page view
* @param {string} section Section name
* @param {Object.<string, string>} pageAttributes An object with page attributes
* @param {Object.<string, string>} userAttributes An object with user attributes
*/
function trackPageView(section, pageAttributes, userAttributes) {
  RNSalesforceDMPModule.trackPageView(index);
}

module.exports = {
  setupTracker,
  trackPageView,
}
