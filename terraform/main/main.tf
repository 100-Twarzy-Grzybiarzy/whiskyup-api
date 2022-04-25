module "iam" {
  source = "../modules/iam"
  prefix = var.prefix
  tags   = var.tags
}

module "whiskybase_table" {
  source = "../modules/dynamodb_table"

  name          = "WhiskyBase"
  partition_key = "WhiskyUrl"

  attributes = [
    {
      name = "WhiskyUrl"
      type = "S"
    }
  ]

  tags = var.tags
}

module "user_table" {
  source = "../modules/dynamodb_table"

  name          = "User"
  partition_key = "PK"
  sort_key      = "SK"

  attributes = [
    {
      name = "PK"
      type = "S"
    }, {
      name = "SK"
      type = "S"
    }
  ]

  tags = var.tags
}

module "new_whisky_queue" {
  source = "../modules/sqs_queue"
  name   = "new.whisky"
  tags   = var.tags
}

module "new_url_queue" {
  source = "../modules/sqs_queue"
  name   = "new.url"
  tags   = var.tags
}