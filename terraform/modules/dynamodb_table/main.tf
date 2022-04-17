resource "aws_dynamodb_table" "dynamodb_table" {
  name         = var.name
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = var.partition_key
  range_key    = var.sort_key

  dynamic "attribute" {
    for_each = var.attributes

    content {
      name = attribute.value.name
      type = attribute.value.type
    }
  }

  tags = var.tags
}