package example

import aecor.data.Folded
import aecor.data.Folded.syntax._

final case class AnalysisState(status: AnalysisState.Status) {
  def applyEvent(e: AnalysisEvent): Folded[AnalysisState] = e match {
    case AnalysisEvent.Created(_, _, _) =>
      impossible
    case AnalysisEvent.ParametersAdded(_) =>
      this.next
    case AnalysisEvent.Finalized =>
      copy(status = AnalysisState.Status.Finalized).next
  }
}

object AnalysisState {
  sealed abstract class Status extends Product with Serializable
  object Status {
    final case object Initialised extends Status
    final case object Finalized extends Status
  }
  def init(e: AnalysisEvent): Folded[AnalysisState] = e match {
    case AnalysisEvent.Created(_, _, _) =>
      AnalysisState(AnalysisState.Status.Initialised).next
    case _ => impossible
  }
}
