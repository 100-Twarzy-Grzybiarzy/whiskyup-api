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
}

variable "tags" {
  type = map(string)
}