variable "name" {
  type = string
}

variable "partition_key" {
  type = string
}

variable "sort_key" {
  type    = string
  default = null
}

variable "attributes" {
  type = list(object({
    name = string
    type = string
  }))
  default = []
}

variable "local_secondary_indexes" {
  type = list(object({
    name               = string
    sort_key           = string
    projection_type    = string
    non_key_attributes = list(string)
  }))
  default = []
}

variable "global_secondary_indexes" {
  type = list(object({
    name               = string
    partition_key      = string
    sort_key           = string
    projection_type    = string
    non_key_attributes = list(string)
  }))
  default = []
}

variable "tags" {
  type = map(string)
}