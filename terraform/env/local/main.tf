terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.49"
    }
  }

  required_version = ">= 0.14.9"
}

variable "aws_region" {
  type = string
  default = "eu-central-1"
}

provider "aws" {
  profile = "local"
  region  = var.aws_region

  access_key = "mock_access_key"
  secret_key = "mock_secret_key"

  s3_force_path_style = true
  skip_credentials_validation = true
  skip_metadata_api_check = true
  skip_requesting_account_id = true

  endpoints {
    dynamodb = "http://localhost:4566"
    sqs = "http://localhost:4566"
    iam = "http://localhost:4566"
  }
}

module "main" {
  source = "../../main"

  prefix = "whiskyup_local"

  aws_region       = var.aws_region
  aws_account_id   = 000000000000
  is_local         = true
  is_develop       = true

  tags = {
    Project  = "whiskyup"
    Instance = "local"
  }
}