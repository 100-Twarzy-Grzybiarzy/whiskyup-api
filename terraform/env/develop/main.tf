terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.49"
    }
  }

  backend "s3" {
    bucket = "whiskyup-develop-resources"
    key    = "terraform/develop/terraform.tfstate"
    region = "eu-central-1"
  }

  required_version = ">= 0.14.9"
}

variable "aws_region" {
  type    = string
  default = "eu-central-1"
}

provider "aws" {
  profile = "default"
  region  = var.aws_region
}

module "main" {
  source = "../../main"

  prefix = "whiskyup_develop"

  aws_region     = var.aws_region
  aws_account_id = 123456789000
  is_develop     = true

  tags = {
    Project  = "WhiskyUp"
    Instance = "develop"
  }
}