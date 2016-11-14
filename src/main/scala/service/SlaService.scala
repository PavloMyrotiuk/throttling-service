package service

import model.Sla

import scala.concurrent.Future

trait SlaService {
    def getSlaByToken(token: String): Future[Sla]
}

trait DelayedSlaService extends SlaService {
    override abstract def getSlaByToken(token: String): Future[Sla] = {
        Thread.sleep(250)
        super.getSlaByToken(token)
    }
}