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
  }))
}

variable "tags" {
  type = map(string)
}