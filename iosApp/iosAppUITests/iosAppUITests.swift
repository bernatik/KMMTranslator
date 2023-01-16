//
//  iosAppUITests.swift
//  iosAppUITests
//
//  Created by Aliaksandr Bernat on 16/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import XCTest

final class iosAppUITests: XCTestCase {
    
    private var app: XCUIApplication!

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.

        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
        app = XCUIApplication()
        app.launchArguments = ["isUiTesting"]
        app.launch()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testRecordAndTranslate(){
        app.buttons["Record audio"].tap()
        
        app.buttons["Voice recorder button tag"].tap()
        app.buttons["Voice recorder button tag"].tap()
        
        XCTAssert(app.staticTexts["Test result"].waitForExistence(timeout: 2))
        
        app.buttons["Voice recorder button tag"].tap()
        
        XCTAssert(app.textViews["Test result"].waitForExistence(timeout: 2))
        
        app.buttons["TRANSLATE"].tap()
        
        XCTAssert(app.staticTexts["Test result"].waitForExistence(timeout: 2))
        XCTAssert(app.staticTexts["Test translation"].waitForExistence(timeout: 2))
    }
}
