package example
import java.util.UUID

import aecor.data.{EventTag, EventsourcedBehavior, Tagging}
import akka.actor.ActorSystem

object AppServer extends App {
  import aecor.runtime.akkapersistence._
  import monix.eval.Task
  import monix.execution.Scheduler.Implicits.global

  val system = ActorSystem("test")

  val journalAdapter = CassandraJournalAdapter(system)

  val runtime: AkkaPersistenceRuntime[UUID] = AkkaPersistenceRuntime(system, journalAdapter)

  val behavior: EventsourcedBehavior[Analysis, Option[AnalysisState], AnalysisEvent] =
    EventsourcedBehavior.optional[Analysis, AnalysisState, AnalysisEvent](
    AnalysisActions,
    AnalysisState.init,
    _.applyEvent(_)
  )

  private val tag = EventTag("Analysis_v2")
  val deploySubscriptions: Task[AnalysisId => Analysis[Task]] =
    runtime.deploy(
      "Analysis",
      behavior.lift[Task],
      Tagging.const[AnalysisId](tag)
    )


  val app: Task[Unit] = for {
    transactions <- deploySubscriptions
    a = transactions(
      AnalysisId(UUID.fromString("32e8b33f-3327-4b3b-96f8-ba127e6840ac")))
    _ <- a.createAnalysis("user id", "product id", "plan id")
    _ <- a.addParameters(List("first", "second"))
    z <- a.close
    _ = system.log.info("Bind result [{}]", z)
  } yield ()


  val x = app.runAsync
  ()

}
