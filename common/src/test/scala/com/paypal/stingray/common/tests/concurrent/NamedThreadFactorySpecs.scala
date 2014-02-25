package com.paypal.stingray.common.tests.concurrent

import org.specs2.Specification
import com.paypal.stingray.common.tests.util.CommonImmutableSpecificationContext
import com.paypal.stingray.common.concurrent.NamedThreadFactory

/**
 * Tests for [[com.paypal.stingray.common.concurrent.NamedThreadFactory]]
 */
class NamedThreadFactorySpecs extends Specification { override def is = s2"""

    NamedThreadFactory provides a method to get DefaultThreadFactory instances with a specified thread name prefix.

    NamedThreadFactory should:

    be able to create NORMAL priority non-daemon threads with given prefix                    ${thread().normal}
    be able to create NORMAL priority daemon threads from root with given prefix              ${thread().normalDaemonRoot}
    be able to create NORMAL priority daemon threads with given prefix                        ${thread().normalDaemon}
    be able to create multiple threads                                                        ${thread().multiple}
  """

  case class thread() extends CommonImmutableSpecificationContext {

    val factory = new NamedThreadFactory(){}
    val noopRunnable = new Runnable(){ def run(){} }
    val prefix = "prefix"

    def normal = apply {
      val t = factory.namedThreadFactory(prefix).newThread(noopRunnable)
      //don't start the thread, just inspect it
      (t.getName must startWith(prefix)) and (t.isDaemon must beFalse) and (t.getPriority must beEqualTo(Thread.NORM_PRIORITY))
    }

    def normalDaemon = apply {
      val t = factory.namedDaemonThreadFactory(prefix).newThread(noopRunnable)
      //don't start the thread, just inspect it
      (t.getName must startWith(prefix)) and (t.isDaemon must beTrue) and (t.getPriority must beEqualTo(Thread.NORM_PRIORITY))
    }

    def normalDaemonRoot = apply {
      val t = factory.namedDaemonRootThreadFactory(prefix).newThread(noopRunnable)
      //don't start the thread, just inspect it
      (t.getName must startWith(prefix)) and (t.isDaemon must beTrue) and (t.getPriority must beEqualTo(Thread.NORM_PRIORITY))
    }

    def multiple = apply {
      val f = factory.namedThreadFactory(prefix)
      val t1 = f.newThread(noopRunnable)
      val t2 = f.newThread(noopRunnable)
      (t1.getName must startWith(prefix)) and (t2.getName must startWith(prefix))
    }
  }

}
