module "iam" {
  source = "../modules/iam"
  prefix = var.prefix
  tags   = var.tags
}

module "whiskyup_table" {
  source = "../modules/dynamodb_table"

  name          = "WhiskyUp"
  partition_key = "PK"
  sort_key      = "SK"

  attributes = [
    {
      name = "PK"
      type = "S"
    }, {
      name = "SK"
      type = "S"
    }, {
      name = "GSI1PK"
      type = "S"
    }, {
      name = "GSI1SK"
      type = "S"
    }, {
      name = "GSI2PK"
      type = "S"
    }, {
      name = "GSI2SK"
      type = "S"
    }, {
      name = "GSI3PK"
      type = "S"
    }, {
      name = "GSI3SK"
      type = "S"
    }, {
      name = "GSI4PK"
      type = "S"
    }, {
      name = "GSI4SK"
      type = "S"
    }
  ]

  global_secondary_indexes = [
    {
      name = "GSI1",
      partition_key = "GSI1PK",
      sort_key = "GSI1SK",
      projection_type = "ALL"
    }, {
      name = "GSI2",
      partition_key = "GSI2PK",
      sort_key = "GSI2SK",
      projection_type = "ALL"
    }, {
      name = "GSI3",
      partition_key = "GSI3PK",
      sort_key = "GSI3SK",
      projection_type = "ALL"
    }, {
      name = "GSI4",
      partition_key = "GSI4PK",
      sort_key = "GSI4SK",
      projection_type = "INCLUDE",
      non_key_attributes = ["Url", "Id"]
    }
  ]

  tags = var.tags
}

module "new_whisky_queue" {
  source = "../modules/sqs_queue"
  name   = "new-whisky"
  tags   = var.tags
}

module "new_url_queue" {
  source = "../modules/sqs_queue"
  name   = "new-url"
  tags   = var.tags
}