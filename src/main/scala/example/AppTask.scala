package example
import java.util.UUID

import aecor.data.{EventTag, EventsourcedBehavior, Tagging}
import akka.actor.ActorSystem

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object AppTask extends App {
  import aecor.runtime.akkapersistence._
  import monix.eval.Task
  import monix.execution.Scheduler.Implicits.global

  val system = ActorSystem("test")

  val journalAdapter = CassandraJournalAdapter(system)

  val runtime: AkkaPersistenceRuntime[UUID] = AkkaPersistenceRuntime(system, journalAdapter)

  val behavior: EventsourcedBehavior[Subscription, Option[SubscriptionState], SubscriptionEvent] =
    EventsourcedBehavior.optional[Subscription, SubscriptionState, SubscriptionEvent](
    SubscriptionActions,
    SubscriptionState.init,
    _.applyEvent(_)
  )

  private val tag = EventTag("Subscription")
  val deploySubscriptions: Task[SubscriptionId => Subscription[Task]] =
    runtime.deploy(
      "Subscription",
      behavior.lift[Task],
      Tagging.const[SubscriptionId](tag)
    )


  val app: Task[Unit] = for {
    transactions <- deploySubscriptions
    a = transactions(SubscriptionId(UUID.fromString("32e8b33f-3327-4b3b-96f8-ba127e6840ab")))
    b <- a.createSubscription("user id", "product id", "plan id")
    c <- a.pauseSubscription
    d <- a.resumeSubscription
    d <- a.cancelSubscription
    _ = system.log.info("Bind result [{}]", d)
//    _ <- Task.fromFuture(system.terminate())
  } yield ()


  val x = app.runAsync
//  val y: Unit = Await.result(x, Duration("5 seconds"))

//  val x: JournalQuery[UUID, SubscriptionId, SubscriptionEvent] = runtime.journal[SubscriptionId, SubscriptionEvent]
//
//  val y = x.eventsByTag(tag, offset = None)
//  y.toMat()
//
  ()

}
