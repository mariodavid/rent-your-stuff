resource "heroku_app" "rent_your_stuff_staging" {
  name   = "rent-your-stuff-staging"
  region = "eu"

  buildpacks = [
    "heroku/gradle"
  ]
}