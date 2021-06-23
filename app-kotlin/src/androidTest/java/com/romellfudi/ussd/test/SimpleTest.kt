package com.romellfudi.ussd.test

import android.Manifest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.romellfudi.ussd.accessibility.view.SplashActivity
import com.romellfudi.ussd.screen.MainScreen
import com.romellfudi.ussdlibrary.USSDServiceKT
import org.junit.Rule
import org.junit.Test

/**
 * In this example you can see a test tuned by default Kaspresso configuration.
 * When you start the test you can see output of default Kaspresso interceptors:
 * - a lot of useful logs
 * - failure handling
 * - screenshots in the device
 * Also you can see the test DSL simplifying the writing of any test
 */
class SimpleTest : TestCase() {
    companion object {
        private val SERVICE_CLASS_NAME = USSDServiceKT::class.java.canonicalName!!
    }

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.SYSTEM_ALERT_WINDOW,
        Manifest.permission.READ_PHONE_STATE
//         , ACTION_MANAGE_OVERLAY_PERMISSION
    )

    @get:Rule
    val activityTestRule = ActivityTestRule(SplashActivity::class.java, true, false)

    @Test
    fun test() =
        before {
            testLogger.i("Before `test` section")
            device.accessibility.disable()
        }.after {
            device.accessibility.disable()
            testLogger.i("After `test` section")
        }.run {
            step("check splash") {
                activityTestRule.launchActivity(null)
                scenario(
                    CheckScenario()
                )
                device.screenshots.take("splash_screenshot")
            }
            step("Make the USSD call & not show the splashView") {
                MainScreen {
                    testLogger.i("Writing the short number in the input...")
                    result { hasText("Empty") }
                    phone {
                        clearText()
                        typeText("*515#")
                    }
                    testLogger.i("Closing soft keyboard...")
                    closeSoftKeyboard()
                    testLogger.i("Enable USSDService accessibility...")
                    device.accessibility.enable(
                        device.targetContext.packageName,
                        SERVICE_CLASS_NAME
                    )
                    testLogger.i("Dial up...")
                    dialUpButton { click() }
                    device.screenshots.take("dial_screenshot")
                }
            }
            step("Verify the result of the sdk") {
                MainScreen {
                    testLogger.i("Scrolling the page and display the result")
                    call_scroll { swipeUp() }
                    result {
                        isDisplayed()
                        hasNoText("Empty")
                        hasAnyText()
                        hasText(RegexMatcher("^\\n-\\n\\[.+(,\\s+.*)*\\]$"))
                    }
                    device.screenshots.take("over_screenshot")
                }
            }
            step("Make the USSD call & show the splashView") {
                MainScreen {
                    testLogger.i("Closing soft keyboard...")
                    closeSoftKeyboard()
                    radioSplash { click() }
                    testLogger.i("Dial up...")
                    dialUpButton { click() }
                    device.screenshots.take("dial_splash_screenshot")
                }
            }
            step("Verify the result of the sdk after showing SplashView") {
                MainScreen {
                    testLogger.i("Scrolling the page and display the result")
                    call_scroll { swipeUp() }
                    result {
                        hasNoText("Empty")
                        hasAnyText()
                        hasText(RegexMatcher("^\\n-\\n\\[.+(,\\s+.*)*\\]$"))
                    }
                    device.screenshots.take("over_screenshot")
                }
            }
        }
}
