var reporter = require('cucumber-html-reporter');

var options = {
    theme: 'bootstrap',
    jsonFile: 'testreportdir/testreport.json',
    output: 'report/ReportCucumber.html',
    brandTitle: 'Automation Report',
    reportSuiteAsScenarios: true,
    scenarioTimestamp: false,
    launchReport: true,
    metadata: {
        "Client": "Template",
        "App Version": "Template",
        "Test Environment": "Template",
        "Platform": "Template",
        "OS": "Template",
        "Manufacturer": "Template",
        "Model": "Template",
        "Build No": "30"
    }
};

reporter.generate(options);