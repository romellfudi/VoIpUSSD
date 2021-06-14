package com.romellfudi.ussd.test

import com.kaspersky.kaspresso.testcases.api.scenario.BaseScenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.romellfudi.ussd.screen.SplashScreen

class CheckScenario<ScenarioData> : BaseScenario<ScenarioData>() {

    override val steps: TestContext<ScenarioData>.() -> Unit = {

        step("Check Visible view") {
            SplashScreen {
                logo {
                    isVisible()
                }
                lottie {
                    isVisible()
                }
            }
        }
    }
}
