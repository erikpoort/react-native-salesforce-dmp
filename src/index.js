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
  return RNSalesforceDMPModule.setupTracker(configId, debugFlag = false);
}

/**
* Track page view
* @param {string} section Section name
* @param {Object.<string, string>} pageAttributes An object with page attributes
* @param {Object.<string, string>} userAttributes An object with user attributes
*/
function trackPageView(section, pageAttributes = {}, userAttributes = {}) {
  return RNSalesforceDMPModule.trackPageView(section, pageAttributes, userAttributes);
}

/**
* Fire an event
* @param {string} event Event name
* @param {Object.<string, string>} attributes An object with event attributes
*/
function fireEvent(event, attributes = {}) {
  return RNSalesforceDMPModule.fireEvent(event, attributes);
}

/**
* Track a transaction
* @param {Object.<string, string>} attributes An object with transaction attributes
*/
function trackTransaction(attributes = {}) {
  return RNSalesforceDMPModule.trackTransaction(attributes);
}

module.exports = {
  setupTracker,
  trackPageView,
  fireEvent,
  trackTransaction,
}
