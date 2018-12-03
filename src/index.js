import { NativeModules } from 'react-native';
const { RNSalesforceDMPModule } = NativeModules;

/**
 * Setup tracker
 * @param {string} configId Configuration Id
 * @param {boolean} debugFlag This outputs debug messages while it is trying to fetch data/send data
 *     to Salesforce DMP backend servers. You should turn this off before the final submission of
 *     your app.
 * @return {promise}
 */
function setupTracker(configId, debugFlag) {
    return RNSalesforceDMPModule.setupTracker(configId, debugFlag = false);
}

/**
 * Get segments
 * @return {promise}
 */
function getSegments() {
    return RNSalesforceDMPModule.getSegments();
}

/**
* Track page view
* @param {string} section Section name
* @param {Object.<string, string>} pageAttributes An object with page attributes
* @param {Object.<string, string>} userAttributes An object with user attributes
* @return {promise}
*/
function trackPageView(section, pageAttributes = {}, userAttributes = {}) {
  return RNSalesforceDMPModule.trackPageView(section, pageAttributes, userAttributes);
}

/**
* Fire an event
* @param {string} event Event name
* @param {Object.<string, string>} attributes An object with event attributes
* @return {promise}
*/
function fireEvent(event, attributes = {}) {
  return RNSalesforceDMPModule.fireEvent(event, attributes);
}

/**
* Track a transaction
* @param {Object.<string, string>} attributes An object with transaction attributes
* @return {promise}
*/
function trackTransaction(attributes = {}) {
  return RNSalesforceDMPModule.trackTransaction(attributes);
}

/**
* Set consent
* @param {Object.<string, string>} attributes An object with attributes
* @return {promise}
*/
function consentSetRequest(attributes) {
  RNSalesforceDMPModule.consentSetRequest(attributes);
}

/**
* Get conset
* @param {Object.<string, string>} attributes An object with attributes
* @return {promise}
*/
function consentGetRequest(attributes) {
  RNSalesforceDMPModule.consentGetRequest(attributes);
}

/**
* Remove customer
* @param {Object.<string, string>} attributes An object with attributes
* @return {promise}
*/
function consumerRemoveRequest(attributes) {
  RNSalesforceDMPModule.consumerRemoveRequest(attributes);
}

/**
* Add consumer portability
* @param {Object.<string, string>} attributes An object with attributes
* @return {promise}
*/
function consumerPortabilityRequest(attributes) {
  RNSalesforceDMPModule.consumerPortabilityRequest(attributes);
}

module.exports = {
  setupTracker,
  getSegments,
  trackPageView,
  fireEvent,
  trackTransaction,
  consentSetRequest,
  consentGetRequest,
  consumerRemoveRequest,
  consumerPortabilityRequest,
}
