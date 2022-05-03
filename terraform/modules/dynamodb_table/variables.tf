terraform {
  experiments = [module_variable_optional_attrs]
}

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
    non_key_attributes = optional(list(string))
  }))
  default = []
}

variable "global_secondary_indexes" {
  type = list(object({
    name               = string
    partition_key      = string
    sort_key           = string
    projection_type    = string
    non_key_attributes = optional(list(string))
  }))
  default = []
}

variable "tags" {
  type = map(string)
}

locals {
  global_secondary_indexes = defaults(var.global_secondary_indexes, {
      non_key_attributes = ""
    }
  )

  local_secondary_indexes = defaults(var.local_secondary_indexes, {
      non_key_attributes = ""
    }
  )
}