resource "aws_iam_policy" "iam_policy" {
  name = "${var.prefix}_policy"
  path = "/"

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "logs:*"
      ],
      "Resource": "arn:aws:logs:*:*:*",
      "Effect": "Allow"
    },{
      "Action": [
        "sqs:*"
      ],
      "Resource": "arn:aws:sqs:*:*:*",
      "Effect": "Allow"
    },{
      "Action": [
        "dynamodb:*"
      ],
      "Resource": "arn:aws:dynamodb:*:*:*",
      "Effect": "Allow"
    }
  ]
}
EOF

  tags = var.tags
}