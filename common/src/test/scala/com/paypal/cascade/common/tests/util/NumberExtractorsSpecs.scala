/**
 * Copyright 2013-2014 PayPal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.paypal.cascade.common.tests.util

import org.specs2._
import org.scalacheck._
import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._
import com.paypal.cascade.common.util.{IntExtractor, LongExtractor}

/**
 * Tests number extractor methods [[com.paypal.cascade.common.util]]
 */
class NumberExtractorsSpecs extends Specification with ScalaCheck { override def is = s2"""

  Convenience methods for converting String to Int/Long.

  For IntExtractor:
    Int is returned when String is successfully converted       ${IntExtract().successCase}
    0 is returned when conversion returns None                  ${IntExtract().failureCase}

  For LongExtractor:
    Long is returned when String is successfully converted      ${LongExtract().successCase}
    0 is returned when conversion returned None                 ${LongExtract().failureCase}

  """

  case class IntExtract() {
    def successCase = forAll(Gen.posNum[Int]) { num =>
      val newInt = num.toString match {
        case IntExtractor(a) => a
        case _ => 0
      }
      newInt must beEqualTo(num)
    }
    def failureCase = forAll(arbitrary[Int], Gen.alphaChar) { (num, char) =>
      val newInt = num.toString + char match {
        case IntExtractor(a) => a
        case _ => 0
      }
      newInt must beEqualTo(0)
    }
  }

  case class LongExtract() {
    def successCase = forAll(Gen.posNum[Long]) { num =>
      val newLong = num.toString match {
        case LongExtractor(a) => a
        case _ => 0L
      }
      newLong must beEqualTo(num)
    }
    def failureCase = forAll(arbitrary[Long], Gen.alphaChar) { (num, char) =>
      val newLong = num.toString + char match {
        case LongExtractor(a) => a
        case _ => 0L
      }
      newLong must beEqualTo(0L)
    }
  }

}
