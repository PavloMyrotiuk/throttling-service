# throttling-service

##Goal

To provide Service Level Agreement (SLA) for our REST endpoint the throttling service should be implemented in order to limit the requests per second (RPS) for each user.

Assume you already have a service, which returns maximum allowed RPS and username of the user by the token from the request 
‘Authorization’ header

```
case class Sla(user:String, rps:Int)

trait SlaService {

def getSlaByToken(token:String):Future[Sla]

}
```

Service to implement

ThrottlingService might look like
```
trait ThrottlingService {

val graceRps:Int // configurable

val slaService: SlaService // use mocks/stubs for testing

// Should return true if the request is within allowed RPS.

def isRequestAllowed(token:Option[String]): Boolean

}
```
##Rules for RPS counting

* If no token provided, assume the client as unauthorized.

* All unauthorized user's requests are limited by GraceRps

* If request has a token, but slaService has not returned any info yet, treat it as unauthorized user

* RPS should be counted per user, as the same user might use different tokens for authorization

* SLA should be counted by intervals of 1/10 second (i.e. if RPS limit is reached, after 1/10 second ThrottlingService should allow 10% more requests)

* SLA information is changed quite rarely and SlaService is quite costly to call (~250ms per request), so consider caching SLA requests. Also, you should not query the service, if the same token request is already in progress.

* Consider that REST service average response time is bellow 5ms, ThrottlingService shouldn’t impact REST service SLA.

##Acceptance Criteria

* Implement ThrottlingService

* Cover the code with the tests that prove the validity of the code

* Implement the load test that proves that for N users, K rsp during T seconds around T*N*K requests were successful. Measure the overhead of using ThrottlingService service, compared with same rest endpoint without ThrottlingService
