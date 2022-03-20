module "iam" {
  source = "../modules/iam"
  prefix = var.prefix
  tags   = var.tags
}

module "whisky_table" {
  source = "../modules/dynamodb_table"

  name      = "${var.prefix}_whisky"
  hash_key  = "id"
  range_key = "name"

  attributes = [
    {
      name = "id"
      type = "S"
    },
    {
      name = "name"
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