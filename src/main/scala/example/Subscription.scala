package example

import aecor.macros.boopickleWireProtocol
import boopickle.Default._

import scala.language.higherKinds

@boopickleWireProtocol
trait Subscription[F[_]] {
  def createSubscription(userId: String, productId: String, planId: String): F[Unit]
  def pauseSubscription: F[Unit]
  def resumeSubscription: F[Unit]
  def cancelSubscription: F[Unit]
}