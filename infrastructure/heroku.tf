resource "heroku_app" "rent_your_stuff_staging" {
  name   = "rent-your-stuff-staging"
  region = "eu"

  buildpacks = [
    "heroku/gradle"
  ]
}

resource "heroku_addon" "rent_your_stuff_staging_db" {
  app  = heroku_app.rent_your_stuff_staging.id
  plan = "heroku-postgresql:hobby-dev"
}


resource "heroku_pipeline" "rent_your_stuff_pipeline" {
  name = "rent-your-stuff-pipeline"
}

# Couple app to pipeline.
resource "heroku_pipeline_coupling" "staging_pipeline_coupling" {
  app      = heroku_app.rent_your_stuff_staging.id
  pipeline = heroku_pipeline.rent_your_stuff_pipeline.id
  stage    = "staging"
}


// Add the GitHub repository integration with the pipeline.
resource "herokux_pipeline_github_integration" "pipeline_integration" {
  pipeline_id = heroku_pipeline.rent_your_stuff_pipeline.id
  org_repo = "mariodavid/rent-your-stuff"
}

// Add Heroku app GitHub integration.
resource "herokux_app_github_integration" "rent_your_stuff_gh_integration" {
  app_id = heroku_app.rent_your_stuff_staging.uuid
  branch = "main"
  auto_deploy = true
  wait_for_ci = true

  # Tells Terraform that this resource must be created/updated
  # only after the `herokux_pipeline_github_integration` has been successfully applied.
  depends_on = [herokux_pipeline_github_integration.pipeline_integration]
}
