variable "aws_region" {
  type    = string
  default = "eu-central-1"
}

variable "aws_account_id" {
  type = string
}

variable "prefix" {
  type = string
}

variable "is_local" {
  type    = bool
  default = false
}

variable "is_develop" {
  type    = bool
  default = false
}

variable "tags" {
  type = map(string)
}