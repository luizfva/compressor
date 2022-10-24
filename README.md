# Compressor API

## Code Assignment description

1. Create a SpringBoot app with an endpoint to receive a list of files (form-data) and
   return a zipped file containing the input files.
2. Package the app in a Docker image and provide the instructions to run.

## Building and running the docker image

On the root folder, run `docker-compose up`, which should build the docker image using the provided `Dockerfile` and
start it up.

### Exposed API

The `POST /compress` API takes a `multipart/form-data` request, reads the `MultipartFiles` from the `files` parameter,
compresses them into a single `download.zip` file that is written to the resulting response.

## Software development process

### Description

Please name the steps you would consider when defining a SDLC for a SaaS application.
Provide a brief description of what is covered by each step and the tools you’d use/setup to
facilitate it.

### SDLC process

The following list gets together a few practices of techniques or practices we should have in place while defining a
SaaS platform SDLC:

#### Premisses

Services must:

- Be self-contained and decoupled and may only communicate with each other via well-defined interfaces
- Be independently deployable
- Have SLOs defined and monitored with alerts generated when they are not met

#### Continuous deployment process

- Local development with emphasis on automated tests. Engineers write unit and integration tests to evaluate business
  rules and integration points. We should try to reduce external dependencies as much as possible, leveraging
  mocks/stubs, contract tests, testcontainers, etc. so services can be implemented in isolation.
- Integration tests should be able to be run locally, but also as part of the integration pipeline
- The integration pipeline is executed every time a change is pushed to the source control repository. The standard
  pipeline would (we can choose to run/skip each pipeline stage depending on the branch the pipeline is being running
  off from ):
    - Checkout the pushed changes
    - Build the project locally
    - Run unit/component/integration/contract tests
    - Run static code analysis
    - Publish artifacts (JAR files, Docker images, helm charts, etc.)
    - Deploy changes to production
- Changes should be wrapped within a Feature Toggle (Feature Flag) that are turned off by default. This allows features
  to be rolled out incrementally for a controlled audience at the right time.
- Pull Requests are mandatory to incorporate a change to the main code base. Pull Requests must be reviewed by a
  different team member to evaluate things such as:
    - Adherence to product requirements
    - Adoption of best practices, checking for bad smells, vulnerabilities, etc.
    - Static code analysis (which also allows checking for bad smells, vulnerabilities, etc.)
    - Reviewing pull requests is also a great learning and knowledge sharing tool.
- Once a Pull Request is approved and merged all the underlying deliverables are published (JAR files, Docker images,
  Helm charts, etc.), meaning they are available for delivery
- When a newer version of a service is available, a canary process is kicked off. The traffic is incrementally shifted
  from the stable to the canary version. A pre-defined set of metrics must be evaluated during this process (e.g.
  error-rate, request latency, nack-rate, etc.). Based off on those metrics, the canary controller must decide whether
  to proceed or to abort the process.
- Feature flags can be turned on for an internal audience for exploratory/manual testing.
