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

module "new_whisky_queue" {
  source = "../modules/sqs_queue"
  name   = "${var.prefix}_new_whisky"
  tags   = var.tags
}

module "new_url_queue" {
  source = "../modules/sqs_queue"
  name   = "${var.prefix}_new_url"
  tags   = var.tags
}