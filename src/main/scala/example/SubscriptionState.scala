package example

import aecor.data.Folded
import aecor.data.Folded.syntax._
import SubscriptionState._
import SubscriptionEvent._
import SubscriptionState.Status._

final case class SubscriptionState(status: Status) {
  def applyEvent(e: SubscriptionEvent): Folded[SubscriptionState] = e match {
    case SubscriptionCreated(_, _, _) =>
      impossible
    case SubscriptionPaused =>
      copy(status = Paused).next
    case SubscriptionResumed =>
      copy(status = Active).next
    case SubscriptionCancelled =>
      copy(status = Cancelled).next
  }
}

object SubscriptionState {
  sealed abstract class Status extends Product with Serializable
  object Status {
    final case object Active extends Status
    final case object Paused extends Status
    final case object Cancelled extends Status
  }
  def init(e: SubscriptionEvent): Folded[SubscriptionState] = e match {
    case SubscriptionCreated(userId, productId, planId) =>
      SubscriptionState(Active).next
    case _ => impossible
  }
}
