# Compressor API

## Assignment description

1. Create a SpringBoot app with an endpoint to receive a list of files (form-data) and
   return a zipped file containing the input files.
2. Package the app in a Docker image and provide the instructions to run.

## Building and running the docker image

On the root folder, run `docker-compose up`, which should build the docker image using the provided `Dockerfile` and
start it up.

### Exposed API

The `POST /compress` API takes a `multipart/form-data` request, reads the `MultipartFiles` from the `files` parameter,
compresses them into a single `download.zip` file that is written to the resulting response.