import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the UserProfile entity.
 */
class UserProfileGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the UserProfile entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJSON
        .check(header.get("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all userProfiles")
            .get("/userservice/api/user-profiles")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new userProfile")
            .post("/userservice/api/user-profiles")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "profileNo":"SAMPLE_TEXT", "mobileNo":"SAMPLE_TEXT", "nick":"SAMPLE_TEXT", "lowercaseNick":"SAMPLE_TEXT", "discription":"SAMPLE_TEXT", "icon":"SAMPLE_TEXT", "icons":"SAMPLE_TEXT", "accountNo":"SAMPLE_TEXT", "profileKey":"SAMPLE_TEXT", "flag":"0", "realName":"SAMPLE_TEXT", "sex":"0", "birthDay":"SAMPLE_TEXT", "provinceId":"0", "cityId":"0", "qq":"SAMPLE_TEXT", "createdTime":"2020-01-01T00:00:00.000Z", "updatedTime":"2020-01-01T00:00:00.000Z", "createdIp":"SAMPLE_TEXT", "appKey":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_userProfile_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created userProfile")
                .get("/userservice${new_userProfile_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created userProfile")
            .delete("/userservice${new_userProfile_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
