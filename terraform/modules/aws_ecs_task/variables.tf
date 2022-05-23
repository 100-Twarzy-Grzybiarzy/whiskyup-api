variable "aws_region" {
  type    = string
  default = "eu-central-1"
}

variable "prefix" {
  type = string
}

variable "role_arn" {
  type = string
}

variable "containers" {
  type = list(object({
    name = string
    image = string
    environment = list(object({
      name = string
      value = string
    }))
  }))
}

variable "tags" {
  type = map(string)
}