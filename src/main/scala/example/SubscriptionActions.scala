package example
import aecor.data.Action
import example.SubscriptionEvent.{SubscriptionCancelled, SubscriptionCreated, SubscriptionPaused, SubscriptionResumed}
import example.SubscriptionState.Status.{Active, Paused}

object SubscriptionActions extends Subscription[Action[Option[SubscriptionState], SubscriptionEvent, ?]] {
  def createSubscription(userId: String, productId: String, planId: String): Action[Option[SubscriptionState], SubscriptionEvent, Unit] =
    Action {
      case Some(subscription) =>
        // Do nothing reply with ()
        List.empty -> ()
      case None =>
        // Produce event and reply with ()
        List(SubscriptionCreated(userId, productId, planId)) -> ()
    }
  def pauseSubscription: Action[Option[SubscriptionState], SubscriptionEvent, Unit] =
    Action {
      case Some(subscription) if subscription.status == Active =>
        List(SubscriptionPaused) -> ()
      case _ =>
        List.empty -> ()
    }
  def resumeSubscription: Action[Option[SubscriptionState], SubscriptionEvent, Unit] =
    Action {
      case Some(subscription) if subscription.status == Paused =>
        List(SubscriptionResumed) -> ()
      case _ =>
        List.empty -> ()
    }

  def cancelSubscription: Action[Option[SubscriptionState], SubscriptionEvent, Unit] =
    Action {
      case Some(subscription) =>
        List(SubscriptionCancelled) -> ()
      case _ =>
        List.empty -> ()
    }
}