resource "aws_sqs_queue" "sqs_queue" {
  name = var.name
  tags = var.tags

  visibility_timeout_seconds = 30
  message_retention_seconds  = 86400
}