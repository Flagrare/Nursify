# Nursify

Nursify is a marketplace application designed to connect healthcare facilities with qualified healthcare professionals (
workers). This project is a Spring Boot Kotlin application that utilizes Gradle as the build system. It also
incorporates JUnit and Testcontainers for testing purposes. The database used is Postgresql, which is containerized
using Docker Compose. To ensure observability, Nursify uses Prometheus data to generate a Grafana-powered dashboard.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)
- [Observability](#observability)
- [Performance](#performance)

## Introduction

The healthcare industry heavily relies on the availability of skilled professionals to fill shift vacancies in various
healthcare facilities. Nursify aims to streamline this process by providing a platform where facilities can post
available shifts and workers can find and apply for suitable positions. The application is built with Spring Boot and
Kotlin to ensure robustness, scalability, and maintainability.

## ⑇ Features

- Workers can search for shifts based on their preferences and qualifications.
- Shift eligibility criteria based on worker and facility attributes.
- Document management system to track and match worker documents.
- Shift grouping by date for easier navigation and selection.
- Error handling and logging for improved reliability.
- API documentation following OpenAPI specification.
    - _Endpoints are also generated based on the OpenAPI yaml file._
- Application observability to allow environment optimization, risk mitigation, and improve API availability.
- Performance optimization to ensure fast and responsive API endpoints.

## Prerequisites

Before installing Nursify, make sure the following components are installed on your system:

- Java Development Kit (JDK) 8 or later
- Docker
- Docker Compose

## 🔧 Installation

1. Clone the Nursify repository to your local machine.
2. Open a terminal and navigate to the project's root directory.
3. Start the Postgresql database using Docker Compose:

   `docker-compose up -d`

4. Build the project using the following command:

   `./gradlew build`

5. Start the Nursify application:

   `./gradlew bootRun`

The application will start running on `http://localhost:8080`.

## 🔎 Usage

1. Ensure that Nursify and the Postgresql database are up and running.
2. Access the provided API endpoints through your preferred method (e.g., API client, web browser).
3. Utilize the API `/shifts` endpoint to search shifts using different filters

> 📄 The endpoint uses pagination and sorting for better results and performance.

> 🔗 You can [use Swagger](http://localhost:8080/swagger-ui/index.html) for your own convenience.

## 🧪 Testing

Nursify includes a comprehensive test suite using JUnit and Testcontainers.

There are over 60 individual tests.
> Around _80%_ coverage for the whole app, over _90%_ coverage for business-related classes.

Although traditional **white box** tests were implemented, the main focus was on the **black box** (integration) set
of tests.

> The belief that testing behavior produces much more value than implementation details guided this project, that's why
> special attention was
> paid to `API REST tests` and the `Controller` layer.
> Also, it enforces the validity of API contracts.

To run the tests, execute the following command:

`./gradlew test`

The tests will verify the functionality and reliability of the backend service.

**OBS:** Postgres `docker` must be `running` for the tests to work.

## 🚨 Observability

To ensure observability, Nursify incorporates Prometheus and Grafana. Prometheus collects metrics from the application,
while Grafana provides a visual representation of these metrics.

To access the Grafana dashboard, follow these steps:

1. Open a web browser and go to _[http://localhost:3000](http://localhost:3000)_.
2. Login using the following Grafana credentials:

     ```
     > login: admin
     > password: nursify
     ```

3. Select
   the [JVM (Micrometer)](http://localhost:3000/d/b8851110-6642-4c76-b3b2-fdf83499a7cc/jvm-micrometer?orgId=1&refresh=5s)
   dashboard.

## ⏱ Performance

> To improve performance, `worker` data (individual information + documentation) and `facility` data (information +
> documentation) are cached to
> improve not only searching, but
> facility
> availability matching,
> since this information should not change very frequently.

Performance tests were extracted using JMeter, following the premise: requests were made using 10 thread groups (10) and
looped over many times.

Two main tests were conducted: one where all `workerIds` were used to get shifts, and one that solely focused on
deterministic workers, where we could guarantee that we would always have results.

Detailed results from those can be found on:

- [aggregate_all_requests_thread_users.csv](reports%2Foutput%2Faggregate_all_requests_thread_users.csv)
- [aggregate_deterministic_thread_users.csv](reports%2Foutput%2Faggregate_deterministic_thread_users.csv)

> The test plan can be found on [NursifyTestPlan.jmx](reports%2Fconfig%2FNursifyTestPlan.jmx)

From those reports, we can draw the following conclusions.

- All cases with 100% error rates will be disregarded. Although the error rate for a lot of the requests is 100%, they
  were expected during the performance testing. The API was intentionally configured to generate errors and test
  error-handling capabilities.

### **case 01**: all requests

- Over 100.500 requests were made;
- 100 threads (users) were used;

#### ► Throughput:

The throughput value is 244.76 requests per second, which indicates a high rate of request processing. This suggests
that the API has good performance in terms of handling a large number of concurrent requests.

#### ► Error Rate:

The error percentage is 0%, indicating that none of the requests encountered errors or failures. The API demonstrates
excellent reliability and stability, ensuring all requests were successfully processed.

#### ► Response Time:

The average response time is 354 milliseconds, with a median of only 3 milliseconds. However, the 90th percentile
response time is 1,208 milliseconds, and the 95th percentile response time is 1,769 milliseconds. This indicates that a
small portion of requests experience significantly longer response times. The 99th percentile response time is even
higher at 3,974 milliseconds, indicating that a very small number of requests encounter very slow responses. While most
requests perform well in terms of response time, efforts should be made to address the outliers and optimize the
performance for slower requests.

#### ► Network Traffic:

The received kilobytes per second and sent kilobytes per second values are 99.92 KB/sec and 131.62 KB/sec, respectively.
This indicates a moderate amount of data being transferred between the client and server per second. Network traffic
should be monitored and optimized to ensure efficient data transmission and minimize any potential bottlenecks.

#### ► Variability:

The variability in the performance metrics can be observed from the minimum and maximum response times. The minimum
response time recorded is 0 milliseconds, indicating some requests received responses almost instantaneously. On the
other hand, the maximum response time is 7,773 milliseconds, suggesting occasional outliers with significantly delayed
responses. This variability in response times should be investigated and addressed to ensure consistent and predictable
performance.


> 💭 **CONCLUSION**
>
> In summary, the API demonstrates no errors, and good throughput in terms of handling concurrent requests. However, the
> longer response times for certain requests and variability in performance indicate areas that require improvement.
> Reducing response time outliers, monitoring network traffic, and addressing performance variability will help enhance
> the overall performance and reliability of the API.

### case 02: deterministic requests

- 12.137 requests were made in total;
- Only workers that would return relevant shift results were used as requests;
- 100 threads (users) were used;

#### ► Throughput:

The throughput of the API is measured at 9.52701 requests per second. This indicates a moderate level of throughput,
suggesting that the API is capable of handling a decent amount of requests within a given time frame.

#### ► Error Rate:

The error percentage recorded during the test is 0%. This implies that no errors or failures occurred during the API
testing, indicating a high level of reliability and stability.

#### ► Response Time:

The average response time is 10,446 milliseconds, with a median response time of 10,487 milliseconds. The 90th
percentile response time (90% Line) is 10,960 milliseconds, and the 95th percentile response time (95% Line) is 11,152
milliseconds. The maximum response time recorded is 20,711 milliseconds. These values indicate that the API's response
time can be categorized as fair to poor, as higher values suggest potential performance issues or delays.

#### ► Network Traffic:

The average received data rate is 34.32 KB/sec, and the average sent data rate is 5.12 KB/sec. These values reflect a
moderate level of network traffic, indicating that the API is transmitting and receiving data at a reasonable rate.

#### ► Variability:

The variability of response times, as observed from the differences between the median, 90th percentile, and 95th
percentile response times, falls within an acceptable range. This suggests a fair to a good level of consistency in the
API's performance.

> 💭 **CONCLUSION**
>
> In conclusion, the API demonstrates a moderate throughput and network traffic, excellent error percentage (no errors),
> fair to poor response time performance, and fair to good variability. Further optimization may be required to improve
> the response time and enhance the overall performance of the API. The throughput and error percentage indicates the
> API's ability to handle requests and its stability.

### ❗ Important:

- None of the tests was made using any `dateRange` filters.

- Considering this fact, the performance outcome is still very positive, since in some cases there were over 10,000 of
  shifts being returned for specific workers.

- Most of the requests that performed not very well had no filters set up beside the `workerId`. When
  using `facilityId` and/or `dateRange` tends to improve the overall API performance.

### 🖥️ Application Monitoring

During the tests, the application was monitored so the parameters such as memory consumption, I/O rate, and CPU usage
could be attested.

![grafana-final.png](reports%2Foutput%2Fgrafana-final.png)

Is possible to notice that, although the performance spiked during the usage, it was not alarming, and most of the
statics
are operating within the bounds of normalcy.

## ⬆️ Areas for improvement

Here are some brief thoughts on aspects of the application that could be improved.

- With more information on `workers` and `facilities`, would be a good idea to filter data (suggested
  filters) based on _location_, _shifts taken in the past_ and _pay rates_
- Would be a good idea to always set up a `dateRange` filter with a `7 days` or `2 weeks` parameter as default.
- Grafana has a lot more potential than just monitoring dashboards. Ideally, alarms and alerts could be configured to
  improve observability and responsiveness to incidents.
- Although `API Rest` endpoint tests + `Controller layer` tests were implemented, the ideal would be to combine both in
  a complete typesafe API testing.
    - Having the declarative aspects of testing the controller layer directly + the
      characteristics of testing the REST endpoints with all characteristics that the web layer encompasses (routing,
      object (de)serialization, servlet filters, aspects from WebMVC and security layer) would result in much more
      efficient
      tests.
    - With more time and research this
      could be done using Feign to implement interfaces from the OpenAPI-generated Controller files - which would make
      the
      maintainability is also a lot easier.
- Matching worker documents with facility requirements could probably be improved using more refined data structure
  techniques such as `hashMaps`, instead of doing it via queries.
- Although we are already caching documentation results, we could expand caching even more:
    - Available shifts could be cached based on date range filter criteria for short periods.
    - Shifts queried by a facility have also great potential for caching since they might have fewer changes.
- To increase API performance, maybe API throttling should be implemented;
- The infrastructure where the application would be deployed would play a major role in its performance. Load balancing
  and adequate machine resources could improve the performance and stability considerably.