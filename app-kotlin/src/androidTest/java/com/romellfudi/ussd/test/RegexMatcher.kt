/*
 * Copyright (c) 2021. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * portfolio.romellfudi.com
 */
package com.romellfudi.ussd.test

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class RegexMatcher(private val regex: String) : TypeSafeMatcher<String>() {
    override fun describeTo(description: Description) {
        description.appendText("matches regular expression=`$regex`")
    }

    public override fun matchesSafely(string: String): Boolean {
        return string.matches(regex.toRegex())
    }
}