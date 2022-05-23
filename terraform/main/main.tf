module "iam" {
  source = "../modules/iam"
  prefix = var.prefix
  tags   = var.tags
}

module "ecs_task" {
  source = "../modules/aws_ecs_task"

  count      = var.is_local ? 0 : 1
  role_arn   = module.iam.ecs_task_role_arn
  prefix     = var.prefix
  aws_region = var.aws_region

  containers = [
    {
      name        = "backend",
      image       = "${local.image_common_uri}/whiskyup/backend:latest"
      environment = [
        {
          name  = "INIT_MODE"
          value = "true"
        }
      ]
    }, {
      name        = "crawler",
      image       = "${local.image_common_uri}/whiskyup/crawler:latest"
      environment = [
        {
          name  = "KNOWN_WHISKIES_URL"
          value = "http://localhost:8080/urls"
        }, {
          name  = "MAX_NUMBER_OF_PAGES_TO_CRAWL_OVER"
          value = "1000"
        }, {
          name  = "URLS_TO_BE_SCRAPPED_QUEUE"
          value = module.new_url_queue.url
        }, {
          name  = "URLS_TO_BE_DELETED_QUEUE"
          value = module.whisky_queue.url
        }
      ]
    }, {
      name        = "scraper",
      image       = "${local.image_common_uri}/whiskyup/scraper:latest"
      environment = [
        {
          name  = "URLS_TO_BE_SCRAPPED_QUEUE"
          value = module.new_url_queue.url
        }, {
          name  = "WHISKIES_TO_BE_ADDED_QUEUE"
          value = module.whisky_queue.url
        }
      ]
    }
  ]

  tags = var.tags
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
      name            = "GSI1",
      partition_key   = "GSI1PK",
      sort_key        = "GSI1SK",
      projection_type = "ALL"
    }, {
      name            = "GSI2",
      partition_key   = "GSI2PK",
      sort_key        = "GSI2SK",
      projection_type = "ALL"
    }, {
      name            = "GSI3",
      partition_key   = "GSI3PK",
      sort_key        = "GSI3SK",
      projection_type = "ALL"
    }, {
      name               = "GSI4",
      partition_key      = "GSI4PK",
      sort_key           = "GSI4SK",
      projection_type    = "INCLUDE",
      non_key_attributes = ["Url", "Id"]
    }
  ]

  tags = var.tags
}

module "whisky_queue" {
  source = "../modules/sqs_queue"
  name   = "whisky"
  tags   = var.tags
}

module "new_url_queue" {
  source = "../modules/sqs_queue"
  name   = "new-url"
  tags   = var.tags
}