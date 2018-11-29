import { NativeModules } from 'react-native';
const { RNSalesforceDMPModule } = NativeModules;

/**
* Setup tracker
* @param {string} configId Configuration Id
* @param {boolean} debugFlag This outputs debug messages while it is trying to fetch data/send data to Salesforce DMP backend servers. You should turn this off before the final submission of your app.
* @param {boolean} dryRunFlag This does a dryRun without sending any data to the Salesforce DMP servers. This is useful while you are developing an app. You should turn this off before the final submission of your app.
*/
function setupTracker(configId, debugFlag, dryRunFlag) {
  RNSalesforceDMPModule.setupTracker(configId, debugFlag, dryRunFlag);
}

module.exports = {
  setupTracker,
}
